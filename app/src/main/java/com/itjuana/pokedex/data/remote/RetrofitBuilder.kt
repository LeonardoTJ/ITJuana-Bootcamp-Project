package com.itjuana.pokedex.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    // Get singleton Retrofit instance
    val instance: PokemonApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(PokemonApi::class.java)
    }
}