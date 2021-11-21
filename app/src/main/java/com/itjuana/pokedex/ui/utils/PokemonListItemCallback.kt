package com.itjuana.pokedex.ui.utils

import com.itjuana.pokedex.data.domain.model.Pokemon

interface PokemonListItemCallback {
    fun onClick(pokemon: Pokemon)
}