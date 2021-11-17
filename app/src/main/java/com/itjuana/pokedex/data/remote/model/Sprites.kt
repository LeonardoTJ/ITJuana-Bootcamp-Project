package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default")
    val spriteFrontDefaultUrl: String?,
    @SerializedName("other")
    val otherSprites: OtherSprites?,
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?,
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val officialArtworkFrontDefaultUrl: String?,
)
