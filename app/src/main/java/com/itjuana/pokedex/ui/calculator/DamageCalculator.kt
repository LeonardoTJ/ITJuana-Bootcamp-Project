package com.itjuana.pokedex.ui.calculator

import com.itjuana.pokedex.data.domain.model.Pokemon
import kotlin.math.round
import kotlin.random.Random

object DamageCalculator {

    fun getRandomLevel(): Int {
        return Random.nextInt(1, 11)
    }

    fun getRandomPower(): Int {
        return Random.nextInt(1, 51)
    }

    fun calculate(
        attackerLevel: Int,
        attackerPower: Int,
        attackerPokemon: Pokemon,
        defenderPokemon: Pokemon
    ): Int {
        val multiplier = getMultiplier(attackerPokemon, defenderPokemon)
        return round(((((2 * attackerLevel) / 5.0) * attackerPower * (attackerPokemon.attack / defenderPokemon.defense)) / 50 + 2) * multiplier)
            .toInt()
    }

    private fun getMultiplier(attackerPokemon: Pokemon, defenderPokemon: Pokemon): Double {
        return when {
            attackerPokemon.doubleDamageTo.contains(defenderPokemon.type) -> {
                2.0
            }
            attackerPokemon.halfDamageTo.contains(defenderPokemon.type) -> {
                0.5
            }
            attackerPokemon.noDamageTo.contains(defenderPokemon.type) -> {
                0.0
            }
            else -> {
                1.0
            }
        }
    }
}