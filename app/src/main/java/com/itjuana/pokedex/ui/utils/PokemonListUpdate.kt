package com.itjuana.pokedex.ui.utils

import com.itjuana.pokedex.data.domain.model.Pokemon

interface PokemonListUpdate {
    fun updateList(newList: List<Pokemon?>)
}