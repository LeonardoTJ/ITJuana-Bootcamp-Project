package com.itjuana.pokedex.ui.pokedex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.data.repository.PokedexRepository

class PokedexViewModel(private val pokedexRepository: PokedexRepository) : ViewModel() {

    private var _selectedPokemon = MutableLiveData<List<Pokemon>>()
    val selectedPokemon: MutableLiveData<List<Pokemon>> = _selectedPokemon

    private var _multiSelectEnabled = MutableLiveData(false)
    val multiSelectEnabled: MutableLiveData<Boolean> = _multiSelectEnabled

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: MutableLiveData<List<Pokemon>> = _pokemonList

    suspend fun getAllPokemon() {
        _pokemonList.value = pokedexRepository.getAllPokemon()
    }

    suspend fun searchPokemon(name: String) {
        _pokemonList.value = pokedexRepository.getAllPokemonByName(name)
    }

    suspend fun getPokemon(name: String): Pokemon? {
        return pokedexRepository.getPokemonByName(name)
    }

    suspend fun isInDatabase(id: Int): Boolean {
        return (pokedexRepository.getPokemonById(id) != null)
    }

    suspend fun addPokemon(pokemon: Pokemon) {
        pokedexRepository.addPokemon(pokemon)
    }

    suspend fun deletePokemon(pokemon: Pokemon) {
        pokedexRepository.deletePokemon(pokemon)
    }
}

class PokedexViewModelFactory(private val pokedexDataSource: PokedexDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(
                pokedexRepository = pokedexDataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}