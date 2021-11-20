package com.itjuana.pokedex.data.remote.source

import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.PokemonApi
import com.itjuana.pokedex.data.remote.model.Stat
import com.itjuana.pokedex.data.repository.SearchPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class PokeApiDataSource(private val pokemonApi: PokemonApi) : SearchPokemonRepository {

    /**
     * Query Pokemon endpoint with provided name
     * Returns Pokemon model object
     */
    override suspend fun searchPokemonByName(name: String): Pokemon? {
        val pokemon = pokemonApi.getPokemonByName(name)
        return Pokemon(
            id = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            spriteUrl = pokemon.spritesResponse.otherSpritesResponse?.officialArtworkResponse?.officialArtworkFrontDefaultUrl
                ?: pokemon.spritesResponse.spriteFrontDefaultUrl,
            hp = pokemon.stats[Stat.HP.ordinal].baseStat,
            attack = pokemon.stats[Stat.ATTACK.ordinal].baseStat,
            defense = pokemon.stats[Stat.DEFENSE.ordinal].baseStat
//            typeSlotResponses = pokemon.typeSlotResponses
        )
    }

    /**
     * Query Pokemon endpoint with provided ID
     * Returns Pokemon model object
     */
    override suspend fun searchPokemonById(id: Int): Pokemon? {
        val pokemon = pokemonApi.getPokemonById(id)
        return Pokemon(
            id = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            spriteUrl = pokemon.spritesResponse.otherSpritesResponse?.officialArtworkResponse?.officialArtworkFrontDefaultUrl
                ?: pokemon.spritesResponse.spriteFrontDefaultUrl,
            hp = pokemon.stats[Stat.HP.ordinal].baseStat,
            attack = pokemon.stats[Stat.ATTACK.ordinal].baseStat,
            defense = pokemon.stats[Stat.DEFENSE.ordinal].baseStat
//            typeSlotResponses = pokemon.typeSlotResponses
        )
    }

    /**
     * Query Pokemon endpoint with a given list of integer IDs
     * Returns a list of Pokemon model objects
     */
    override suspend fun getPokemonList(idList: List<Int>): List<Pokemon>? {
        val pokemonList = mutableListOf<Pokemon>()

        withContext(Dispatchers.IO) {
            idList.map { pokemonId ->
                async { pokemonApi.getPokemonByName(pokemonId.toString()) }
            }.awaitAll().map { pokemon ->
                pokemonList.add(
                    Pokemon(
                    id = pokemon.id,
                    name = pokemon.name,
                    height = pokemon.height,
                    weight = pokemon.weight,
                    spriteUrl = pokemon.spritesResponse.otherSpritesResponse?.officialArtworkResponse?.officialArtworkFrontDefaultUrl
                        ?: pokemon.spritesResponse.spriteFrontDefaultUrl,
                    hp = pokemon.stats[Stat.HP.ordinal].baseStat,
                    attack = pokemon.stats[Stat.ATTACK.ordinal].baseStat,
                    defense = pokemon.stats[Stat.DEFENSE.ordinal].baseStat,
                )
                )
            }
        }
        return pokemonList
    }
}