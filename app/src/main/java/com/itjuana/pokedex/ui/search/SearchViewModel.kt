package com.itjuana.pokedex.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.data.local.model.Pokemon
import com.itjuana.pokedex.data.remote.RetrofitBuilder
import com.itjuana.pokedex.data.remote.repository.SearchPokemonRepository
import com.itjuana.pokedex.data.remote.source.PokemonDataSource
import com.itjuana.pokedex.util.Status

class SearchViewModel(private val searchPokemonRepository: SearchPokemonRepository) : ViewModel() {

    // Status of current request
    private val _status = MutableLiveData<Status>(Status.EMPTY)
    var status: LiveData<Status> = _status
    // Pokemon info
    private val _pokemon = MutableLiveData<Pokemon?>()
    var pokemon: LiveData<Pokemon?> = _pokemon

    suspend fun getPokemonByNameOrId(name: String) {
        _status.value = Status.LOADING
        try {
            _pokemon.value = searchPokemonRepository.search(name)
            _status.value = Status.SUCCESS
        } catch (e: Exception) {
            _pokemon.value = null
            _status.value = Status.ERROR
            e.printStackTrace()
        }
    }

    /**
     *  Factory method for search fragment viewModel
     */
    class SearchViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(
                    searchPokemonRepository = PokemonDataSource(RetrofitBuilder.instance)
                ) as T
            }

            throw Exception("Class type not supported.")
        }
    }
}