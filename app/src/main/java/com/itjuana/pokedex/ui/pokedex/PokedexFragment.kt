package com.itjuana.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.databinding.FragmentPokedexBinding

class PokedexFragment : Fragment() {

    private lateinit var pokedexViewModel: PokedexViewModel
    private lateinit var binding: FragmentPokedexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pokedexViewModel =
            ViewModelProvider(this).get(PokedexViewModel::class.java)

        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPokedex
        pokedexViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}