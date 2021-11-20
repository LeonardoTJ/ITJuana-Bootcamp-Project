package com.itjuana.pokedex.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itjuana.pokedex.data.local.model.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokedexDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokedexDao
}