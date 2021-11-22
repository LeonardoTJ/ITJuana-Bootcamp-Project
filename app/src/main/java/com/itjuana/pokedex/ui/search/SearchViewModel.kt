package com.itjuana.pokedex.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.RetrofitBuilder
import com.itjuana.pokedex.data.remote.source.PokeApiDataSource
import com.itjuana.pokedex.data.repository.SearchPokemonRepository
import com.itjuana.pokedex.ui.utils.Status
import retrofit2.HttpException

class SearchViewModel(
    private val searchPokemonRepository: SearchPokemonRepository
) : ViewModel() {

    // Status of current request
    private val _status = MutableLiveData<Status>(Status.EMPTY)
    var status: LiveData<Status> = _status

    // Pokemon info
    private val _pokemon = MutableLiveData<Pokemon?>()
    var pokemon: LiveData<Pokemon?> = _pokemon

    // Search history
    private val _searchHistory = MutableLiveData<List<Pokemon>>()
    var searchHistory: LiveData<List<Pokemon>> = _searchHistory

    suspend fun getPokemonByNameOrId(name: String) {
        try {
            _pokemon.value = searchPokemonRepository.searchPokemonByName(name)
            _status.value = Status.SUCCESS
        } catch (e: HttpException) {
            _pokemon.value = null
            e.printStackTrace()
        }
    }

    fun addToHistory(pokemonList: List<Pokemon>) {
        _searchHistory.postValue(pokemonList)
        _pokemon.value = null
        _status.value = Status.SUCCESS
    }

    /**
     *  Factory method for search fragment viewModel
     */
    class SearchViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(
                    searchPokemonRepository = PokeApiDataSource(RetrofitBuilder.instance)
                ) as T
            }

            throw Exception("Unknown ViewModel class.")
        }
    }
}