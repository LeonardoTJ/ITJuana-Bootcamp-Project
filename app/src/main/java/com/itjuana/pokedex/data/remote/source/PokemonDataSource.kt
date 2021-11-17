package com.itjuana.pokedex.data.remote.source

import android.util.Log
import com.itjuana.pokedex.data.remote.PokemonApi
import com.itjuana.pokedex.data.remote.model.Pokemon
import com.itjuana.pokedex.data.remote.repository.SearchPokemonRepository
import retrofit2.await

class PokemonDataSource(private val pokemonApi: PokemonApi): SearchPokemonRepository {

    // Query Pokemon endpoint with provided name or ID
    // Returning Pokemon model object
    override suspend fun search(name: String): Pokemon {
        val pokemon = pokemonApi.getPokemonByNameOrId(name).await()
        Log.d("PokemonDataSource", "$pokemon")
        return Pokemon(
            id = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            sprites = pokemon.sprites,
            stats = pokemon.stats,
            typeSlots = pokemon.typeSlots
        )
    }
}