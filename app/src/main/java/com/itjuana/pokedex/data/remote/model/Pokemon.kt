package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("stats")
    val stats: List<Stats>,
    @SerializedName("types")
    val typeSlots: List<TypeSlot>,
)