package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

enum class Stat {
    HP,
    ATTACK,
    DEFENSE,
    SP_ATTACK,
    SP_DEFENSE,
    SPEED
}

data class StatsResponse(
    @SerializedName("base_stat")
    val baseStat: Int
)
