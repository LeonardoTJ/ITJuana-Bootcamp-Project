package com.itjuana.pokedex.data.remote

import com.itjuana.pokedex.data.remote.model.Type
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

    fun validatePokemonSearchTerm(input: String?): String {
        // If name is null or blank string
        if (input.isNullOrEmpty()) return ""
        // If an ID was provided
        val isDigit = input.all { Character.isDigit(it) }
        if (isDigit) {
            // Check if it is in range
            if (Integer.valueOf(input) < 1 || Integer.valueOf(input) > MAX_POKEMON_ID) return ""

            return input
        }
        // If it is an alphabetic name
        val newName = input.trim().lowercase().replaceFirstChar { it.uppercase() }
        if (newName.contains('-')) {
            return newName.split('-')[0]
        }
        return newName
    }

    fun getTypeIdFromUrl(url: String): Type {
        val typeUrl = url.split('/')
        return Type.fromInt(typeUrl[typeUrl.size - 2].toInt())
    }
}