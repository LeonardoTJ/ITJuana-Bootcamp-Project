package com.itjuana.pokedex.ui.calculator

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itjuana.pokedex.utils.buildBulbasaur
import com.itjuana.pokedex.utils.buildPikachu
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DamageCalculatorTest: TestCase() {

    @Test
    fun testDamage() {
        val bulbasaur = buildBulbasaur()
        val pikachu = buildPikachu()
        assert(DamageCalculator.calculate(7, 20, bulbasaur, pikachu) == 4)
        assert(DamageCalculator.calculate(4, 15, pikachu, bulbasaur) == 2)
    }
}