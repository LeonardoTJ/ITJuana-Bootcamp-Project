package com.itjuana.pokedex.utils

import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.model.Type

fun buildBulbasaur(): Pokemon {
    return Pokemon(
        id = 1,
        name = "Bulbasaur",
        height = "0.7m",
        weight = "6.9kg",
        frontSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
        backSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
        officialArtwork = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        hp = 45,
        attack = 49,
        defense = 49,
        spAttack = 65,
        spDefense = 65,
        speed = 45,
        type = Type.GRASS,
        doubleDamageFrom = listOf(Type.FLYING, Type.POISON, Type.BUG, Type.FIRE, Type.ICE),
        doubleDamageTo = listOf(Type.GROUND, Type.ROCK, Type.WATER),
        halfDamageFrom = listOf(Type.GROUND, Type.WATER, Type.GRASS, Type.ELECTRIC),
        halfDamageTo = listOf(
            Type.FLYING,
            Type.POISON,
            Type.BUG,
            Type.STEEL,
            Type.FIRE,
            Type.GRASS,
            Type.DRAGON
        ),
        noDamageFrom = emptyList(),
        noDamageTo = emptyList()
    )
}

fun buildPikachu(): Pokemon {
    return Pokemon(
        id = 25,
        name = "Pikachu",
        height = "0.4m",
        weight = "0.6kg",
        frontSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
        backSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/25.png",
        officialArtwork = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
        hp = 35,
        attack = 55,
        defense = 40,
        spAttack = 50,
        spDefense = 50,
        speed = 90,
        type = Type.ELECTRIC,
        doubleDamageFrom = listOf(Type.GROUND),
        doubleDamageTo = listOf(Type.FLYING, Type.WATER),
        halfDamageFrom = listOf(Type.FLYING, Type.STEEL, Type.ELECTRIC),
        halfDamageTo = listOf(
            Type.GRASS,
            Type.ELECTRIC,
            Type.DRAGON,
        ),
        noDamageFrom = emptyList(),
        noDamageTo = listOf(Type.GROUND)
    )
}