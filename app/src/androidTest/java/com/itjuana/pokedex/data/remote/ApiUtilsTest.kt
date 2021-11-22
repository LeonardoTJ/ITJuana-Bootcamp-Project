package com.itjuana.pokedex.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itjuana.pokedex.data.remote.model.Type
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApiUtilsTest: TestCase() {

    @Test
    fun getTypeIdTest() {
        assert(ApiUtils.getTypeIdFromUrl("https://pokeapi.co/api/v2/type/3/") == Type.FLYING)
        assert(ApiUtils.getTypeIdFromUrl("https://pokeapi.co/api/v2/type/7/") == Type.BUG)
        assert(ApiUtils.getTypeIdFromUrl("https://pokeapi.co/api/v2/type/10/") == Type.FIRE)
        assert(ApiUtils.getTypeIdFromUrl("https://pokeapi.co/api/v2/type/13/") == Type.ELECTRIC)
        assert(ApiUtils.getTypeIdFromUrl("https://pokeapi.co/api/v2/type/18/") == Type.FAIRY)
    }

    @Test
    fun searchQueryValidation() {
        assert(ApiUtils.validatePokemonSearchTerm("bulbasaur") == "Bulbasaur")
        assert(ApiUtils.validatePokemonSearchTerm("95696") == "")
        assert(ApiUtils.validatePokemonSearchTerm(" 345") == "345")
    }
}