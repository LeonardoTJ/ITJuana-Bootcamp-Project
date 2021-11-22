package com.itjuana.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itjuana.pokedex.PokedexApplication
import com.itjuana.pokedex.R
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.databinding.FragmentPokedexBinding
import com.itjuana.pokedex.ui.utils.PokemonAdapter
import com.itjuana.pokedex.ui.utils.PokemonListItemCallback
import com.itjuana.pokedex.ui.utils.PokemonListUpdate
import com.itjuana.pokedex.ui.utils.Status
import kotlinx.coroutines.launch

class PokedexFragment : Fragment(), PokemonListItemCallback {

    private val pokedexViewModel: PokedexViewModel by activityViewModels {
        PokedexViewModelFactory(PokedexDataSource((activity?.application as PokedexApplication).database.pokemonDao()))
    }
    private lateinit var binding: FragmentPokedexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonAdapter(this)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        // Populate viewModel with pokemon in database
        viewLifecycleOwner.lifecycleScope.launch {
            pokedexViewModel.getAllPokemon()
        }
        // Observe if Pokemon list is empty
        pokedexViewModel.status.observe(this.viewLifecycleOwner, { pokemonListStatus ->
            binding.noResultsLayout.visibility = when (pokemonListStatus) {
                Status.ERROR, Status.EMPTY -> {
                    View.VISIBLE
                }
                else -> {
                    View.GONE
                }
            }
        })
        // Observe pokemon list for updates
        pokedexViewModel.pokemonList.observe(this.viewLifecycleOwner, { pokemonList ->
            (recyclerView.adapter as PokemonListUpdate).updateList(pokemonList)
        })
        // Observe Pokemon selection mode for damage comparison
        // Show selected Pokemon at the bottom
        pokedexViewModel.defenderSelectMode.observe(this.viewLifecycleOwner, { selectMode ->
            if (selectMode) {
                binding.pokemon = pokedexViewModel.attackerPokemon.value
                binding.standardBottomSheet.visibility = View.VISIBLE
            } else {
                binding.pokemon = null
                binding.standardBottomSheet.visibility = View.GONE
            }
        })

        // Update pokemon list as the user types a pokemon name
        // If query is blank, show all pokemon in database
        binding.pokemonSearchEditText.doAfterTextChanged { query ->
            if (query != null) {
                if (query.toString().isNotBlank()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        pokedexViewModel.searchPokemon(query.toString())
                    }
                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        pokedexViewModel.getAllPokemon()
                    }
                }
            }
        }

        binding.standardBottomSheet.setOnClickListener {
            pokedexViewModel.clearSelectMode()
        }
    }

    /**
     * Create Dialog to pick attacker Pokemon for damage calculator, or show Pokemon details
     */
    override fun onClick(pokemon: Pokemon) {
        if (pokedexViewModel.defenderSelectMode.value == false) {
            MaterialAlertDialogBuilder(requireContext())
                .setCancelable(true)
                .setNegativeButton(R.string.cancel_button_text) { _, _ ->
                    // Cancel operation
                }
                .setPositiveButton(getString(R.string.calculator_button_text)) { _, _ ->
                    // Select Pokemon for damage calculator
                    pokedexViewModel.selectPokemon(pokemon)
                    if (pokedexViewModel.defenderSelectMode.value == true) {
                        Toast.makeText(
                            requireContext(),
                            "Please select another pokemon or tap bottom to cancel",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .setNeutralButton(getString(R.string.details_button_text)) { _, _ ->
                    // Go to Pokemon details screen
                    val action =
                        PokedexFragmentDirections.actionNavigationPokedexToPokemonDetailFragment(
                            pokemon
                        )
                    findNavController().navigate(action)
                }
                .create().show()
        } else {
            val action =
                PokedexFragmentDirections.actionNavigationPokedexToDamageCalculatorFragment(
                    pokedexViewModel.attackerPokemon.value!!,
                    pokemon
                )
            findNavController().navigate(action)
            pokedexViewModel.clearSelectMode()
        }
    }
}