package com.itjuana.pokedex.ui.pokedex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.data.repository.PokedexRepository

class PokedexViewModel(private val pokedexRepository: PokedexRepository) : ViewModel() {

    private var _attackerPokemon = MutableLiveData<Pokemon?>()
    val attackerPokemon: MutableLiveData<Pokemon?> = _attackerPokemon

    private var _defenderPokemon = MutableLiveData<Pokemon?>()
    val defenderPokemon: MutableLiveData<Pokemon?> = _defenderPokemon

    private var _defenderSelectMode = MutableLiveData(false)
    val defenderSelectMode: MutableLiveData<Boolean> = _defenderSelectMode

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

    fun selectPokemon(pokemon: Pokemon) {
        Log.d(
            "PokedexViewModel",
            "selectMode: ${_defenderSelectMode.value}, atk pokemon: ${_attackerPokemon.value}, def pokemon: ${_defenderPokemon.value}"
        )
        if (_defenderSelectMode.value == true) {
            _defenderPokemon.value = pokemon
            clearSelectMode()
        } else {
            _attackerPokemon.value = pokemon
        }
        _defenderSelectMode.value = _defenderSelectMode.value?.not()
    }

    fun clearSelectMode() {
        _defenderSelectMode.value = false
        _attackerPokemon.value = null
        _defenderPokemon.value = null
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