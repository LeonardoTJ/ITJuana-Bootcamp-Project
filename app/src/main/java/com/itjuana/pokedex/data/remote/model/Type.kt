package com.itjuana.pokedex.data.remote.model

import com.google.gson.annotations.SerializedName

enum class Type(val id: Int) {
    NORMAL(1),
    FIGHTING(2),
    FLYING(3),
    POISON(4),
    GROUND(5),
    ROCK(6),
    BUG(7),
    GHOST(8),
    STEEL(9),
    FIRE(10),
    WATER(11),
    GRASS(12),
    ELECTRIC(13),
    PSYCHIC(14),
    ICE(15),
    DRAGON(16),
    DARK(17),
    FAIRY(18);

    companion object {
        fun fromInt(value: Int) = values().first { it.id == value }
    }
}

data class TypeUrlSlotResponse(
    @SerializedName("type")
    val typeUrlSlot: TypeUrlSlot
)

data class TypeUrlSlot(
    @SerializedName("url")
    val url: String
)

data class TypeResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("damage_relations")
    val damageRelation: DamageRelationSlot
)

data class DamageRelationSlot(
    @SerializedName("double_damage_from")
    val doubleDamageFrom: List<DamageType>,
    @SerializedName("double_damage_to")
    val doubleDamageTo: List<DamageType>,
    @SerializedName("half_damage_from")
    val halfDamageFrom: List<DamageType>,
    @SerializedName("half_damage_to")
    val halfDamageTo: List<DamageType>,
    @SerializedName("no_damage_from")
    val noDamageFrom: List<DamageType>,
    @SerializedName("no_damage_to")
    val noDamageTo: List<DamageType>,
)

data class DamageType(
    @SerializedName("url")
    val url: String
)