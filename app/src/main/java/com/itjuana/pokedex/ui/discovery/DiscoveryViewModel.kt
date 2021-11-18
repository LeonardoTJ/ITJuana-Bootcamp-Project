package com.itjuana.pokedex.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.data.local.model.Pokemon
import com.itjuana.pokedex.data.remote.RetrofitBuilder
import com.itjuana.pokedex.data.remote.repository.SearchPokemonRepository
import com.itjuana.pokedex.data.remote.source.PokemonDataSource
import com.itjuana.pokedex.util.ApiUtils
import com.itjuana.pokedex.util.Status
import kotlin.random.Random

class DiscoveryViewModel(private val searchPokemonRepository: SearchPokemonRepository) :
    ViewModel() {

    // Status of current request
    private val _status = MutableLiveData<Status>(Status.EMPTY)
    var status: LiveData<Status> = _status

    // Pokemon list
    private val _pokemonList = MutableLiveData<List<Pokemon?>>()
    var pokemonList: LiveData<List<Pokemon?>> = _pokemonList

    /**
     * Query Pokemon API for 10 random pokemon
     */
    suspend fun getDiscoveryPokemon() {
        _status.value = Status.LOADING
        try {
            _pokemonList.value = searchPokemonRepository.getPokemonList(ApiUtils.getRandomPokemonIdList())
            _status.value = Status.SUCCESS
        } catch (e: Exception) {
            _pokemonList.value = listOf()
            _status.value = Status.ERROR
            e.printStackTrace()
        }
    }

    /**
     *  Factory method for Pokemon discovery viewModel
     */
    class PokemonDiscoveryFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DiscoveryViewModel::class.java)){
                return DiscoveryViewModel(
                    searchPokemonRepository = PokemonDataSource(RetrofitBuilder.instance)
                ) as T
            }
            throw java.lang.Exception("No class type supported")
        }
    }
}