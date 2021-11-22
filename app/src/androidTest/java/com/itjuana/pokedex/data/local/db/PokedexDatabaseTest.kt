package com.itjuana.pokedex.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itjuana.pokedex.data.domain.model.toEntity
import com.itjuana.pokedex.utils.buildBulbasaur
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokedexDatabaseTest : TestCase() {
    private lateinit var pokedexDb: PokedexDatabase
    private lateinit var pokedexDao: PokedexDao

    @Before
    public override fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        pokedexDb = Room.inMemoryDatabaseBuilder(context, PokedexDatabase::class.java)
            .build()
        pokedexDao = pokedexDb.pokemonDao()
    }

    @After
    fun closeDatabase() {
        pokedexDb.close()
    }

    @Test
    fun writeAndReadPokedex() = runBlocking {
        val pokemon = buildBulbasaur().toEntity()
        pokedexDao.insertPokemon(pokemon)
        val pokemonList = pokedexDao.getAllPokemon()
        assert(pokemonList.contains(pokemon))
    }
}