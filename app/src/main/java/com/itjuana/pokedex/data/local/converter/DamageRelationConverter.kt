package com.itjuana.pokedex.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DamageRelationConverter {

    companion object {
        @Volatile
        private var gson: Gson? = null

        private val type = object : TypeToken<List<Int>>() {}.type

        private fun getInstance(): Gson {
            return gson ?: synchronized(this) {
                val instance = Gson()
                gson = instance
                return instance
            }
        }
    }

    @TypeConverter
    fun toDamageRelationList(damageRelationJson: String): List<Int> {
        return getInstance().fromJson(damageRelationJson, type)
    }

    @TypeConverter
    fun toDamageRelationJson(damageRelationList: List<Int>): String {
        return getInstance().toJson(damageRelationList, type)
    }

}