package com.itjuana.pokedex.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.databinding.FragmentDiscoveryBinding
import com.itjuana.pokedex.ui.utils.PokemonAdapter
import com.itjuana.pokedex.ui.utils.PokemonListItemCallback
import com.itjuana.pokedex.ui.utils.PokemonListUpdate
import kotlinx.coroutines.launch

class DiscoveryFragment : Fragment(), PokemonListItemCallback {

    private lateinit var discoveryViewModel: DiscoveryViewModel
    private lateinit var binding: FragmentDiscoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain view model using factory
        val factory = DiscoveryViewModel.PokemonDiscoveryFactory()
        discoveryViewModel =
            ViewModelProvider(this, factory).get(DiscoveryViewModel::class.java)

        // Set data binding for Pokemon discovery layout
        binding = FragmentDiscoveryBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.discoveryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PokemonAdapter(this)

        // Populate viewModel with random pokemon
        viewLifecycleOwner.lifecycleScope.launch {
            discoveryViewModel.getDiscoveryPokemon()
        }
        // Observe pokemon list for updates
        discoveryViewModel.pokemonList.observe(viewLifecycleOwner, { pokemonList ->
            (recyclerView.adapter as PokemonListUpdate).updateList(pokemonList)
        })
    }

    override fun onClick(pokemon: Pokemon) {
        val action =
            DiscoveryFragmentDirections.actionNavigationDiscoveryToPokemonDetailFragment(pokemon)
        findNavController().navigate(action)
    }
}