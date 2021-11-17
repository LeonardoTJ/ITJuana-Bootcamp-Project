package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base_stat")
    val baseStat: Int
)
