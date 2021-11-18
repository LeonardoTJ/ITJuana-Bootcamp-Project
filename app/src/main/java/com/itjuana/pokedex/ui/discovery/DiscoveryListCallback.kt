package com.itjuana.pokedex.ui.discovery

import com.itjuana.pokedex.data.local.model.Pokemon

interface DiscoveryListCallback {
    fun onClick(pokemon: Pokemon)
}