package com.itjuana.pokedex.data.local.db

import androidx.room.*
import com.itjuana.pokedex.data.local.model.PokemonEntity

@Dao
interface PokedexDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon ORDER BY name ASC")
    suspend fun getAllPokemon(): List<PokemonEntity>?

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%'")
    suspend fun getAllPokemonByName(name: String): List<PokemonEntity>?

    @Query("SELECT * FROM pokemon WHERE name = :name")
    suspend fun getPokemonByName(name: String): PokemonEntity?

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonEntity?

    @Delete
    suspend fun deletePokemon(pokemon: PokemonEntity)
}