package com.itjuana.pokedex.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itjuana.pokedex.data.local.model.Pokemon
import com.itjuana.pokedex.databinding.PokemonDetailFragmentBinding

class PokemonDetailFragment : Fragment() {

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
}