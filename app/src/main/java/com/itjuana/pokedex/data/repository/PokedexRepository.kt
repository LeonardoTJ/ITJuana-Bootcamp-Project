package com.itjuana.pokedex.data.repository

import com.itjuana.pokedex.data.domain.model.Pokemon

interface PokedexRepository {
    suspend fun getAllPokemon(): List<Pokemon>?
    suspend fun getAllPokemonByName(name: String): List<Pokemon>?
    suspend fun getPokemonByName(name: String): Pokemon?
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun addPokemon(pokemon: Pokemon)
    suspend fun deletePokemon(pokemon: Pokemon)
}