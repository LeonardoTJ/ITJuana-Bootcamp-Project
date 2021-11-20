package com.itjuana.pokedex.data.local.source

import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.domain.model.toEntity
import com.itjuana.pokedex.data.local.db.PokedexDao
import com.itjuana.pokedex.data.local.model.toPokemon
import com.itjuana.pokedex.data.repository.PokedexRepository
import com.itjuana.pokedex.data.repository.SearchPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokedexDataSource(private val pokedexDao: PokedexDao): PokedexRepository {

    /**
     * Query Pokedex database for a list of all saved Pokemon model objects
     */
    override suspend fun getAllPokemon(): List<Pokemon>? {
        return pokedexDao.getAllPokemon()?.map { pokemonEntity ->
            pokemonEntity.toPokemon()
        }
    }

    override suspend fun getAllPokemonByName(name: String): List<Pokemon>? {
        return pokedexDao.getAllPokemonByName(name)?.map { pokemonEntity ->
            pokemonEntity.toPokemon()
        }
    }

    override suspend fun getPokemonByName(name: String): Pokemon? {
        return pokedexDao.getPokemonByName(name)?.toPokemon()
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokedexDao.getPokemonById(id)?.toPokemon()
    }

    /**
     * Add a pokemon to the database
     */
    override suspend fun addPokemon(pokemon: Pokemon) {
        pokedexDao.insertPokemon(pokemon.toEntity())
    }

    /**
     * Delete a pokemon from the database
     */
    override suspend fun deletePokemon(pokemon: Pokemon) {
        pokedexDao.deletePokemon(pokemon.toEntity())
    }
}