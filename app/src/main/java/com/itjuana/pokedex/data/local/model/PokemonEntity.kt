package com.itjuana.pokedex.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.model.Type

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: String,
    val weight: String,
    @ColumnInfo(name = "sprite_url")
    val spriteUrl: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    @ColumnInfo(name = "sp_attack")
    val spAttack: Int,
    @ColumnInfo(name = "sp_defense")
    val spDefense: Int,
    val speed: Int,
    val type: Int,
    val doubleDamageFrom: List<Int>,
    val doubleDamageTo: List<Int>,
    val halfDamageFrom: List<Int>,
    val halfDamageTo: List<Int>,
    val noDamageFrom: List<Int>,
    val noDamageTo: List<Int>,
)

fun PokemonEntity.toPokemon() = Pokemon(
    id,
    name,
    height,
    weight,
    spriteUrl,
    hp,
    attack,
    defense,
    spAttack,
    spDefense,
    speed,
    Type.fromInt(type),
    doubleDamageFrom.map { type -> Type.fromInt(type) },
    doubleDamageTo.map { type -> Type.fromInt(type) },
    halfDamageFrom.map { type -> Type.fromInt(type) },
    halfDamageTo.map { type -> Type.fromInt(type) },
    noDamageFrom.map { type -> Type.fromInt(type) },
    noDamageTo.map { type -> Type.fromInt(type) },
)