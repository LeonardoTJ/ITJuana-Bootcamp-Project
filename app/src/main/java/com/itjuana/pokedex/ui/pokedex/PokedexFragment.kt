package com.itjuana.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itjuana.pokedex.PokemonApplication
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.databinding.FragmentPokedexBinding
import com.itjuana.pokedex.ui.utils.PokemonListItemCallback
import kotlinx.coroutines.launch

class PokedexFragment : Fragment(), PokemonListItemCallback {

    private val pokedexViewModel: PokedexViewModel by activityViewModels {
        PokedexViewModelFactory(PokedexDataSource((activity?.application as PokemonApplication).database.pokemonDao()))
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

        val adapter = PokedexAdapter(this)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        // Populate viewModel with pokemon in database
        viewLifecycleOwner.lifecycleScope.launch {
            pokedexViewModel.getAllPokemon()
        }
        // Observe pokemon list for updates
        pokedexViewModel.pokemonList.observe(this.viewLifecycleOwner, { pokemonList ->
            (recyclerView.adapter as PokedexAdapter).updateList(pokemonList)
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
    }

    override fun onClick(pokemon: Pokemon) {
        MaterialAlertDialogBuilder(requireContext())
            .setCancelable(true)
            .setNegativeButton("Cancel") { _, _ ->

            }
            .setNeutralButton("Calculator") { _, _ ->

            }
            .setNeutralButton("Details") { _, _ ->
                val action =
                    PokedexFragmentDirections.actionNavigationPokedexToPokemonDetailFragment(pokemon)
                findNavController().navigate(action)
            }
            .create().show()
    }

}