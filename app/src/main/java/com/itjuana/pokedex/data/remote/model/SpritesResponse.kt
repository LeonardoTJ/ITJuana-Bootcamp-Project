package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class SpritesResponse(
    @SerializedName("front_default")
    val spriteFrontDefaultUrl: String?,
    @SerializedName("other")
    val otherSpritesResponse: OtherSpritesResponse?,
)

data class OtherSpritesResponse(
    @SerializedName("official-artwork")
    val officialArtworkResponse: OfficialArtworkResponse?,
)

data class OfficialArtworkResponse(
    @SerializedName("front_default")
    val officialArtworkFrontDefaultUrl: String?,
)
