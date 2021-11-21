package com.itjuana.pokedex.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itjuana.pokedex.MainActivity
import com.itjuana.pokedex.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        val factory = SplashViewModel.SplashScreenFactory()
        splashViewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)

        splashViewModel.mainScreenFlag.observe(this) { shouldStart ->
            if (shouldStart) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}