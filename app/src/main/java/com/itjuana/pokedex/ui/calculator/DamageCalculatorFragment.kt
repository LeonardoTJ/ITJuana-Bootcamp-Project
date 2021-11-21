package com.itjuana.pokedex.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.itjuana.pokedex.R
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.databinding.DamageCalculatorFragmentBinding

class DamageCalculatorFragment : Fragment() {

    private lateinit var binding: DamageCalculatorFragmentBinding
    private lateinit var selectedAttackerPokemon: Pokemon
    private lateinit var selectedDefenderPokemon: Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()

        selectedAttackerPokemon = arguments?.getSerializable("attackerPokemon") as Pokemon
        selectedDefenderPokemon = arguments?.getSerializable("defenderPokemon") as Pokemon

        binding = DamageCalculatorFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            attackerPokemon = selectedAttackerPokemon
            defenderPokemon = selectedDefenderPokemon
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calculatorTitle.text = getString(
            R.string.calculator_title_text,
            selectedAttackerPokemon.name,
            selectedDefenderPokemon.name
        )
        val attackerLevel = DamageCalculator.getRandomLevel()
        val attackerPower = DamageCalculator.getRandomPower()

        binding.attackerPokemonName.text = getString(
            R.string.pokemon_level_name,
            attackerLevel,
            selectedAttackerPokemon.name
        )
        binding.attackerPokemonAtk.text =
            getString(R.string.stat_atk, selectedAttackerPokemon.attack)
        binding.attackerPokemonDef.text =
            getString(R.string.stat_def, selectedAttackerPokemon.defense)
        binding.attackerPokemonPower.text = getString(R.string.stat_power, attackerPower)

        binding.defenderPokemonName.text = selectedDefenderPokemon.name
        binding.defenderPokemonAtk.text =
            getString(R.string.stat_atk, selectedDefenderPokemon.attack)
        binding.defenderPokemonDef.text =
            getString(R.string.stat_def, selectedDefenderPokemon.defense)

        binding.damageText.text = getString(
            R.string.damage_text_message,
            DamageCalculator.calculate(
                attackerLevel,
                attackerPower,
                selectedAttackerPokemon,
                selectedDefenderPokemon
            )
        )

        binding.closeButton.setOnClickListener {
            val action = DamageCalculatorFragmentDirections.actionDamageCalculatorFragmentPop()
            findNavController().navigate(action)
        }
    }
}