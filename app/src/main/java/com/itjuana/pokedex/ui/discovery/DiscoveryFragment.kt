package com.itjuana.pokedex.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.itjuana.pokedex.databinding.FragmentDiscoveryBinding

class DiscoveryFragment : Fragment() {

    private lateinit var discoveryViewModel: DiscoveryViewModel
    private lateinit var binding: FragmentDiscoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        discoveryViewModel =
            ViewModelProvider(this).get(DiscoveryViewModel::class.java)

        binding = FragmentDiscoveryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDiscovery
        discoveryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}