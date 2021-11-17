package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class TypeSlot(
    @SerializedName("type")
    val type: Type
)

data class Type(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
