package com.itjuana.pokedex.data.remote.repository

import com.itjuana.pokedex.data.local.model.Pokemon

interface SearchPokemonRepository {
    suspend fun search(name: String): Pokemon?
}