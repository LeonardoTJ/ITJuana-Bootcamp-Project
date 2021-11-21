package com.itjuana.pokedex.ui.search

import android.os.Bundle
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
import kotlinx.coroutines.launch
import java.util.*

class SearchFragment : Fragment(), PokemonListItemCallback {

    private val POKEMON_SEARCH_HISTORY = "searchHistory"
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    private val searchHistory = mutableListOf<Pokemon>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = SearchViewModel.SearchViewModelFactory(this, savedInstanceState)
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

        val searchQueryText = binding.pokemonSearchEditText
        val searchButton = binding.pokemonSearchSubmit

        searchButton.setOnClickListener {
            // Check for empty input or containing whitespace
            val queryText = searchQueryText.text.toString()
            if (queryText.isNotBlank()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    searchViewModel.getPokemonByNameOrId(
                        queryText.lowercase(Locale.getDefault())
                    )
                }
            } else {
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }

        searchViewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            if (pokemon != null) {
                searchHistory.add(pokemon)
                searchViewModel.addToHistory(searchHistory)

                (recyclerView.adapter as PokemonListUpdate).updateList(searchHistory)
                val action =
                    SearchFragmentDirections.actionNavigationSearchToPokemonDetailFragment(pokemon)
                findNavController().navigate(action)
            }
        })
    }

    override fun onClick(pokemon: Pokemon) {
        val action =
            SearchFragmentDirections.actionNavigationSearchToPokemonDetailFragment(pokemon)
        findNavController().navigate(action)
    }
}