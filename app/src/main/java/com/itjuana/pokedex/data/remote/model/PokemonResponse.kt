package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("sprites")
    val spritesResponse: SpritesResponse,
    @SerializedName("stats")
    val stats: List<StatsResponse>,
    @SerializedName("types")
    val typeSlotResponses: List<TypeSlotResponse>,
)

data class PokemonListResponse(
    @SerializedName("results")
    val results:List<PokemonListResponseItem>
)

data class PokemonListResponseItem(
    @SerializedName("name")
    val name: String
)