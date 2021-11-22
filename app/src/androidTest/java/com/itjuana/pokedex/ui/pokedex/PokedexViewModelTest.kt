package com.itjuana.pokedex.ui.pokedex

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itjuana.pokedex.PokedexApplication
import com.itjuana.pokedex.data.local.db.PokedexDao
import com.itjuana.pokedex.data.local.db.PokedexDatabase
import com.itjuana.pokedex.data.local.source.PokedexDataSource
import com.itjuana.pokedex.utils.buildBulbasaur
import junit.framework.TestCase
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokedexViewModelTest : TestCase() {
    private lateinit var pokedexViewModel: PokedexViewModel
    private lateinit var pokedexDb: PokedexDatabase
    private lateinit var pokedexDao: PokedexDao

    @Before
    public override fun setUp() {
        super.setUp()

        val context: Context = ApplicationProvider.getApplicationContext()
        pokedexDb = Room.inMemoryDatabaseBuilder(context, PokedexDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        pokedexDao = pokedexDb.pokemonDao()
        pokedexViewModel =
            PokedexViewModelFactory(PokedexDataSource(pokedexDao)).create(PokedexViewModel::class.java)
    }


    @After
    fun closeDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        (context as PokedexApplication).database.close()
    }

    @Test
    fun addAndDeleteFromPokedex() {
        val bulbasaur = buildBulbasaur()
        val pikachu = buildBulbasaur()
        pokedexViewModel.viewModelScope.launch {
            pokedexViewModel.addPokemon(bulbasaur)
            assert(pokedexViewModel.isInDatabase(bulbasaur.id))

            pokedexViewModel.addPokemon(pikachu)
            assert(pokedexViewModel.isInDatabase(pikachu.id))

            pokedexViewModel.deletePokemon(bulbasaur)
            assert(!pokedexViewModel.isInDatabase(bulbasaur.id))

            pokedexViewModel.deletePokemon(pikachu)
            assert(!pokedexViewModel.isInDatabase(pikachu.id))
        }
    }

    @Test
    fun damageCalculatorSelectionTest() {
        val bulbasaur = buildBulbasaur()
        val pikachu = buildBulbasaur()

        pokedexViewModel.viewModelScope.launch {
            pokedexViewModel.addPokemon(bulbasaur)
            pokedexViewModel.addPokemon(pikachu)

            pokedexViewModel.selectPokemon(bulbasaur)
            assert(pokedexViewModel.attackerPokemon.value == bulbasaur)
            assert(pokedexViewModel.defenderSelectMode.value == true)

            pokedexViewModel.selectPokemon(pikachu)
            assert(pokedexViewModel.defenderPokemon.value == pikachu)
            assert(pokedexViewModel.defenderSelectMode.value == false)
        }
    }
}