package com.itjuana.pokedex.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscoveryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>("Discovery fragment")
    val text: LiveData<String> = _text
}