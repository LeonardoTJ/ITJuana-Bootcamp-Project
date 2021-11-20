package com.itjuana.pokedex.data.local.db

import android.content.Context
import androidx.room.Room

class DatabaseManager {

    companion object {
        /**
         * Keep instance in memory so threads always access the most up to date value
         */
        @Volatile
        var INSTANCE: PokedexDatabase? = null

        /**
         * Create instance in synchronized block to limit access to one thread at a time
         * making sure the instance gets created only once
         */
        fun initializeDatabase(context: Context): PokedexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokedexDatabase::class.java,
                    "pokemon_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}