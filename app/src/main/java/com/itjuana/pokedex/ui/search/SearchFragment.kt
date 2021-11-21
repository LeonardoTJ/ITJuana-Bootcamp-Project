package com.itjuana.pokedex.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.databinding.FragmentSearchBinding
import com.itjuana.pokedex.ui.utils.PokemonAdapter
import com.itjuana.pokedex.ui.utils.PokemonListItemCallback
import com.itjuana.pokedex.ui.utils.PokemonListUpdate
import com.itjuana.pokedex.util.ApiUtils
import kotlinx.coroutines.launch
import java.util.*

class SearchFragment : Fragment(), PokemonListItemCallback {

    private val POKEMON_SEARCH_HISTORY = "searchHistory"
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    private val currentSessionSearchHistory = mutableListOf<Pokemon>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = SearchViewModel.SearchViewModelFactory()
        searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchFragment.searchViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonAdapter(this)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        // Observe search history for updates
        searchViewModel.searchHistory.observe(viewLifecycleOwner, { pokemonList ->
            (recyclerView.adapter as PokemonListUpdate).updateList(pokemonList)
        })

        val searchQueryText = binding.pokemonSearchEditText
        val searchButton = binding.pokemonSearchSubmit
        Log.d("SearchFragment", "onViewCreated called, pokemon: ${searchViewModel.pokemon.value}, status: ${searchViewModel.status.value}")

        searchButton.setOnClickListener {
            // Check for empty input or containing whitespace
            val name = searchQueryText.text.toString()
            val formattedName = ApiUtils.validateName(name)
            // If name or ID seems valid, search for Pokemon
            if (formattedName.isNotBlank()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    searchViewModel.getPokemonByNameOrId(formattedName)
                }
            } else {
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe for search result and add to search history
        searchViewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            if (pokemon != null) {
                currentSessionSearchHistory.add(pokemon)
                searchViewModel.addToHistory(currentSessionSearchHistory)
                showPokemonDetails(pokemon)
            } else {
                Toast.makeText(requireContext(), "No results", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onClick(pokemon: Pokemon) {
        showPokemonDetails(pokemon)
    }

    private fun showPokemonDetails(pokemon: Pokemon) {
        val action =
            SearchFragmentDirections.actionNavigationSearchToPokemonDetailFragment(pokemon)
        findNavController().navigate(action)
    }
}