package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class TypeSlotResponse(
    @SerializedName("type")
    val typeResponse: TypeResponse
)

data class TypeResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
