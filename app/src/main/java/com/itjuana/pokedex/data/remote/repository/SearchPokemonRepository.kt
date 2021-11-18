package com.itjuana.pokedex.data.remote.repository

import com.itjuana.pokedex.data.local.model.Pokemon

interface SearchPokemonRepository {
    suspend fun searchPokemon(name: String): Pokemon?
    suspend fun getPokemonList(idList: List<Int>): List<Pokemon>?
}