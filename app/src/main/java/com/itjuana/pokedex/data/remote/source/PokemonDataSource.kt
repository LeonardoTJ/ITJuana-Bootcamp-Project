package com.itjuana.pokedex.data.remote.source

import com.itjuana.pokedex.data.local.model.Pokemon
import com.itjuana.pokedex.data.remote.PokemonApi
import com.itjuana.pokedex.data.remote.model.Stat
import com.itjuana.pokedex.data.remote.repository.SearchPokemonRepository
import retrofit2.await

class PokemonDataSource(private val pokemonApi: PokemonApi) : SearchPokemonRepository {

    // Query Pokemon endpoint with provided name or ID
    // Returning Pokemon model object
    override suspend fun search(name: String): Pokemon {
        val pokemon = pokemonApi.getPokemonByNameOrId(name).await()
        return Pokemon(
            id = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            spriteUrl = pokemon.spritesResponse.otherSpritesResponse?.officialArtworkResponse?.officialArtworkFrontDefaultUrl
                ?: pokemon.spritesResponse.spriteFrontDefaultUrl,
            hp = pokemon.stats[Stat.HP.ordinal].baseStat,
            attack = pokemon.stats[Stat.ATTACK.ordinal].baseStat,
            defense = pokemon.stats[Stat.DEFENSE.ordinal].baseStat,
//            typeSlotResponses = pokemon.typeSlotResponses
        )
    }
}