package com.itjuana.pokedex.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PokedexViewModel : ViewModel() {

    private val _text = MutableLiveData("Pokedex Fragment")
    val text: LiveData<String> = _text
}