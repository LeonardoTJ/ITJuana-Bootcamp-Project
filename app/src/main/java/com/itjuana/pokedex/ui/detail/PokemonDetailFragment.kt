package com.itjuana.pokedex.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itjuana.pokedex.PokemonApplication
import com.itjuana.pokedex.R
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.databinding.PokemonDetailFragmentBinding
import com.itjuana.pokedex.ui.pokedex.PokedexViewModel
import com.itjuana.pokedex.ui.pokedex.PokedexViewModelFactory
import kotlinx.coroutines.launch

class PokemonDetailFragment : BottomSheetDialogFragment() {

    private val pokedexViewModel: PokedexViewModel by activityViewModels {
        PokedexViewModelFactory(PokedexDataSource((activity?.application as PokemonApplication).database.pokemonDao()))
    }

    private lateinit var binding: PokemonDetailFragmentBinding
    private lateinit var queriedPokemon: Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        queriedPokemon = arguments?.getSerializable("pokemonArg") as Pokemon

        // Set data binding for Pokemon detail layout
        binding = PokemonDetailFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            pokemon = queriedPokemon
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("DetailFragment", "onViewCreated called")
        viewLifecycleOwner.lifecycleScope.launch {
            if (pokedexViewModel.isInDatabase(queriedPokemon.id)) {
                setPokedexButton(
                    R.drawable.ic_delete_pokemon,
                    getString(R.string.pokedex_deleted_message, queriedPokemon.name)
                )
            } else {
                setPokedexButton(
                    R.drawable.ic_add_pokemon,
                    getString(R.string.pokedex_added_message, queriedPokemon.name)
                )
            }
        }

        binding.closeButton.setOnClickListener {
            val action = PokemonDetailFragmentDirections.actionPokemonDetailFragmentPop()
            findNavController().navigate(action)
        }
    }

    private fun setPokedexButton(iconId: Int, toastMessage: String) {
        binding.pokedexButton.setImageResource(iconId)
        binding.pokedexButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (iconId == R.drawable.ic_delete_pokemon) {
                    pokedexViewModel.deletePokemon(queriedPokemon)
                } else {
                    pokedexViewModel.addPokemon(queriedPokemon)
                }
            }
            Toast.makeText(
                requireContext(),
                toastMessage,
                Toast.LENGTH_SHORT
            ).show()
            val action = PokemonDetailFragmentDirections.actionPokemonDetailFragmentPop()
            findNavController().navigate(action)
        }
    }
}