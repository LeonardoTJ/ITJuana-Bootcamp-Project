package com.itjuana.pokedex.data.remote.source

import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.PokemonApi
import com.itjuana.pokedex.data.remote.model.PokemonResponse
import com.itjuana.pokedex.data.remote.model.Stat
import com.itjuana.pokedex.data.remote.model.Type
import com.itjuana.pokedex.data.repository.SearchPokemonRepository
import com.itjuana.pokedex.util.ApiUtils
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
        val pokemonResponse = pokemonApi.getPokemonByName(name)
        return buildPokemon(pokemonResponse)
    }

    /**
     * Query Pokemon endpoint with provided ID
     * Returns Pokemon model object
     */
    override suspend fun searchPokemonById(id: Int): Pokemon? {
        val pokemonResponse = pokemonApi.getPokemonById(id)
        return buildPokemon(pokemonResponse)
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
                pokemonList.add(buildPokemon(pokemon))
            }
        }
        return pokemonList
    }

    private suspend fun buildPokemon(pokemonResponse: PokemonResponse): Pokemon {

        // Take first Pokemon type from URL
        val type = getTypeIdFromUrl(pokemonResponse.typeUrlSlotRespons[0].typeUrlSlot.url)
        // Get battle damage info from Pokemon type
        val damageRelationResponse = withContext(Dispatchers.IO) {
            async { pokemonApi.getType(type.id).damageRelation }
        }.await()

        return Pokemon(
            id = pokemonResponse.id,
            name = ApiUtils.validateName(pokemonResponse.name),
            height = "${pokemonResponse.height / 10.0}m",
            weight = "${pokemonResponse.weight / 10.0}kg",
            spriteUrl = pokemonResponse.spritesResponse.otherSpritesResponse?.officialArtworkResponse?.officialArtworkFrontDefaultUrl,
            hp = pokemonResponse.stats[Stat.HP.ordinal].baseStat,
            attack = pokemonResponse.stats[Stat.ATTACK.ordinal].baseStat,
            defense = pokemonResponse.stats[Stat.DEFENSE.ordinal].baseStat,
            spAttack = pokemonResponse.stats[Stat.SP_ATTACK.ordinal].baseStat,
            spDefense = pokemonResponse.stats[Stat.SP_DEFENSE.ordinal].baseStat,
            speed = pokemonResponse.stats[Stat.SPEED.ordinal].baseStat,
            type = type,
            doubleDamageFrom = damageRelationResponse.doubleDamageFrom.map { typeSlot ->
                getTypeIdFromUrl(
                    typeSlot.url
                )
            },
            doubleDamageTo = damageRelationResponse.doubleDamageTo.map { typeSlot ->
                getTypeIdFromUrl(
                    typeSlot.url
                )
            },
            halfDamageFrom = damageRelationResponse.halfDamageFrom.map { typeSlot ->
                getTypeIdFromUrl(
                    typeSlot.url
                )
            },
            halfDamageTo = damageRelationResponse.halfDamageTo.map { typeSlot ->
                getTypeIdFromUrl(
                    typeSlot.url
                )
            },
            noDamageFrom = damageRelationResponse.noDamageFrom.map { typeSlot ->
                getTypeIdFromUrl(
                    typeSlot.url
                )
            },
            noDamageTo = damageRelationResponse.noDamageTo.map { typeSlot ->
                getTypeIdFromUrl(
                    typeSlot.url
                )
            }
        )
    }

    private fun getTypeIdFromUrl(url: String): Type {
        val typeUrl = url.split('/')
        return Type.fromInt(typeUrl[typeUrl.size - 2].toInt())
    }
}