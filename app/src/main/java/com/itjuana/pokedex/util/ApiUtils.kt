package com.itjuana.pokedex.util

import kotlin.random.Random

object ApiUtils {
    const val MAX_POKEMON_ID = 896
    const val DISCOVERY_LIST_SIZE = 10

    /**
     *  Obtain a list of unique Pokemon IDs to populate the Discovery fragment
     */
    fun getRandomPokemonIdList(): List<Int> {
        val idSet = hashSetOf<Int>()

        repeat(DISCOVERY_LIST_SIZE) {
            idSet.add(Random.nextInt(1, MAX_POKEMON_ID))
        }

        return idSet.toList()
    }
}