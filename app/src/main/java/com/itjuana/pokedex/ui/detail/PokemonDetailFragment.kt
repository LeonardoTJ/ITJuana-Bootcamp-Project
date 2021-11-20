package com.itjuana.pokedex.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itjuana.pokedex.PokemonApplication
import com.itjuana.pokedex.R
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.databinding.PokemonDetailFragmentBinding
import com.itjuana.pokedex.ui.pokedex.PokedexViewModel
import com.itjuana.pokedex.ui.pokedex.PokedexViewModelFactory
import kotlinx.coroutines.launch

class PokemonDetailFragment : Fragment() {

    private val pokedexViewModel: PokedexViewModel by activityViewModels {
        PokedexViewModelFactory(PokedexDataSource((activity?.application as PokemonApplication).database.pokemonDao()))
    }

    private lateinit var binding: PokemonDetailFragmentBinding
    private lateinit var queriedPokemon: Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        viewLifecycleOwner.lifecycleScope.launch {
            if (pokedexViewModel.isInDatabase(queriedPokemon.id)) {
                binding.pokedexButton.setImageResource(R.drawable.ic_delete_pokemon)
                binding.pokedexButton.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        pokedexViewModel.deletePokemon(queriedPokemon)
                    }
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.pokedex_deleted_message, queriedPokemon.name),
                        Toast.LENGTH_SHORT
                    ).show()
                    val action = PokemonDetailFragmentDirections.actionPokemonDetailFragmentPop()
                    findNavController().navigate(action)
                }
            } else {
                binding.pokedexButton.setImageResource(R.drawable.ic_add_pokemon)
                binding.pokedexButton.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        pokedexViewModel.addPokemon(queriedPokemon)
                    }
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.pokedex_added_message, queriedPokemon.name),
                        Toast.LENGTH_SHORT
                    ).show()
                    val action = PokemonDetailFragmentDirections.actionPokemonDetailFragmentPop()
                    findNavController().navigate(action)
                }
            }
        }


    }
}