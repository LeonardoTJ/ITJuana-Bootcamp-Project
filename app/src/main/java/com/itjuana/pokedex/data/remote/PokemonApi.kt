package com.itjuana.pokedex.data.remote

import com.itjuana.pokedex.data.remote.model.PokemonListResponse
import com.itjuana.pokedex.data.remote.model.PokemonResponse
import com.itjuana.pokedex.data.remote.model.TypeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    /**
     * Get a Pokemon from its name
     */
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): PokemonResponse

    /**
     * Get a Pokemon from its ID
     */
    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): PokemonResponse

    /**
     * Get a list of contiguous Pokemon starting at a specified ID offset
     */
    @GET("pokemon")
    suspend fun getPokemonListByOffset(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    /**
     * Get Pokemon type damage parameters for a given type ID
     */
    @GET("type/{typeId}")
    suspend fun getType(@Path("typeId") typeId: Int): TypeResponse
}