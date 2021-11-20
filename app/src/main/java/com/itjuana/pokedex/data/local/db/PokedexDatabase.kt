package com.itjuana.pokedex.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itjuana.pokedex.data.local.converter.DamageRelationConverter
import com.itjuana.pokedex.data.local.model.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DamageRelationConverter::class)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokedexDao
}