package com.itjuana.pokedex.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.domain.model.toEntity
import com.itjuana.pokedex.data.local.model.PokemonEntity
import com.itjuana.pokedex.data.remote.model.Type
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
        val pokemon = buildPokemon()
        pokedexDao.insertPokemon(pokemon)
        val pokemonList = pokedexDao.getAllPokemon()
        assert(pokemonList.contains(pokemon))
    }

    private fun buildPokemon(): PokemonEntity {
        return Pokemon(
            id = 1,
            name = "bulbasaur",
            height = "0.7m",
            weight = "6.9kg",
            frontSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            backSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
            officialArtwork = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            hp = 45,
            attack = 49,
            defense = 49,
            spAttack = 65,
            spDefense = 65,
            speed = 45,
            type = Type.GRASS,
            doubleDamageFrom = listOf(Type.FLYING, Type.POISON, Type.BUG, Type.FIRE, Type.ICE),
            doubleDamageTo = listOf(Type.GROUND, Type.ROCK, Type.WATER),
            halfDamageFrom = listOf(Type.GROUND, Type.WATER, Type.GRASS, Type.ELECTRIC),
            halfDamageTo = listOf(
                Type.FLYING,
                Type.POISON,
                Type.BUG,
                Type.STEEL,
                Type.FIRE,
                Type.GRASS,
                Type.DRAGON
            ),
            noDamageFrom = emptyList(),
            noDamageTo = emptyList()
        ).toEntity()
    }
}