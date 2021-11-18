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
import com.itjuana.pokedex.data.local.model.Pokemon
import com.itjuana.pokedex.databinding.FragmentDiscoveryBinding
import kotlinx.coroutines.launch

class DiscoveryFragment : Fragment(), DiscoveryListCallback {

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
        recyclerView.adapter = DiscoveryAdapter(this)

        viewLifecycleOwner.lifecycleScope.launch {
            discoveryViewModel.getDiscoveryPokemon()
        }

        discoveryViewModel.pokemonList.observe(viewLifecycleOwner, { pokemonList ->
            (recyclerView.adapter as DiscoveryAdapter).updateList(pokemonList)
        })
    }

    override fun onClick(pokemon: Pokemon) {
        val action = DiscoveryFragmentDirections.actionNavigationDiscoveryToPokemonDetailFragment(pokemon)
        findNavController().navigate(action)
    }
}