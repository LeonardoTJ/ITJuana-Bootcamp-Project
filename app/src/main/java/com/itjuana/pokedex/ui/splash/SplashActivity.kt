package com.itjuana.pokedex.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.MainActivity
import com.itjuana.pokedex.R

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