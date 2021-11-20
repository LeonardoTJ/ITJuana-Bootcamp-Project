package com.itjuana.pokedex.data.repository

import com.itjuana.pokedex.data.domain.model.Pokemon

interface SearchPokemonRepository {
    suspend fun searchPokemonByName(name: String): Pokemon?
    suspend fun searchPokemonById(id: Int): Pokemon?
    suspend fun getPokemonList(idList: List<Int>): List<Pokemon>?
}