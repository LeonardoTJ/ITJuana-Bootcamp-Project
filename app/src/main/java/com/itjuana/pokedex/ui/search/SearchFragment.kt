package com.itjuana.pokedex.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itjuana.pokedex.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        searchViewModel =
//            ViewModelProvider(this).get(SearchViewModel::class.java)
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

        val searchQueryText = binding.pokemonSearchEditText
        val searchButton = binding.pokemonSearchSubmit
        searchButton.setOnClickListener {
            if (!searchQueryText.text.isNullOrEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    searchViewModel.getPokemonByNameOrId(searchQueryText.text.toString())
                }
            }
        }
    }
}