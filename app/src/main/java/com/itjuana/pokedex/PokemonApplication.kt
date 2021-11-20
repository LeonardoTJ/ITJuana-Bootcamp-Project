package com.itjuana.pokedex

import android.app.Application
import androidx.room.Database
import com.itjuana.pokedex.data.local.db.DatabaseManager
import com.itjuana.pokedex.data.local.db.PokedexDatabase

class PokemonApplication: Application() {

    /**
     * Database with application context is created on first access
     */
    val database: PokedexDatabase by lazy { DatabaseManager.initializeDatabase(this) }
}