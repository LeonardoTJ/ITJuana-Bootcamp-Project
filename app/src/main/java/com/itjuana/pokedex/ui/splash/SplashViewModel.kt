package com.itjuana.pokedex.ui.splash

import android.os.Handler
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel: ViewModel() {

    // Initialization flag
    private val _mainScreenFlag = MutableLiveData(false)
    val mainScreenFlag: LiveData<Boolean> = _mainScreenFlag

    init {
        viewModelScope.launch {
            delay(3000)
            _mainScreenFlag.value = true
        }
    }

    class SplashScreenFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                return SplashViewModel() as T
            }
            throw Exception("Unknown ViewModel class")
        }
    }
}