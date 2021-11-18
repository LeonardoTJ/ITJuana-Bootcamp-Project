package com.itjuana.pokedex.data.local.model

import java.io.Serializable

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val spriteUrl: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int
) : Serializable
