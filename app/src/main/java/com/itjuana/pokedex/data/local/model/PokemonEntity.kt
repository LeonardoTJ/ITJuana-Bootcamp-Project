package com.itjuana.pokedex.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itjuana.pokedex.data.domain.model.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val spriteUrl: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int
)

fun PokemonEntity.toPokemon() = Pokemon(
    id,
    name,
    height,
    weight,
    spriteUrl,
    hp,
    attack,
    defense
)