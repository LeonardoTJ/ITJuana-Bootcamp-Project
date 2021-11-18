package com.itjuana.pokedex.data.remote

import com.itjuana.pokedex.data.remote.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon/{name}")
    fun getPokemonByNameOrId(@Path("name") name: String): Call<PokemonResponse>
}