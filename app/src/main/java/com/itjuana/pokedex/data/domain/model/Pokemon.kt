package com.itjuana.pokedex.data.domain.model

import com.itjuana.pokedex.data.local.model.PokemonEntity
import com.itjuana.pokedex.data.remote.model.Type
import java.io.Serializable

data class Pokemon(
    val id: Int,
    val name: String,
    val height: String,
    val weight: String,
    val frontSprite: String,
    val backSprite: String,
    val officialArtwork: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val spAttack: Int,
    val spDefense: Int,
    val speed: Int,
    val type: Type,
    val doubleDamageFrom: List<Type>,
    val doubleDamageTo: List<Type>,
    val halfDamageFrom: List<Type>,
    val halfDamageTo: List<Type>,
    val noDamageFrom: List<Type>,
    val noDamageTo: List<Type>,
) : Serializable

fun Pokemon.toEntity() = PokemonEntity(
    id,
    name,
    height,
    weight,
    frontSprite,
    backSprite,
    officialArtwork,
    hp,
    attack,
    defense,
    spAttack,
    spDefense,
    speed,
    type.id,
    doubleDamageFrom.map { type -> type.id },
    doubleDamageTo.map { type -> type.id },
    halfDamageFrom.map { type -> type.id },
    halfDamageTo.map { type -> type.id },
    noDamageFrom.map { type -> type.id },
    noDamageTo.map { type -> type.id },
)