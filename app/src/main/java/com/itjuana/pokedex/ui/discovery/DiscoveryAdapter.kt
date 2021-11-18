package com.itjuana.pokedex.ui.discovery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itjuana.pokedex.data.local.model.Pokemon
import com.itjuana.pokedex.databinding.PokemonItemBinding

class DiscoveryAdapter(private val callback: DiscoveryListCallback) : RecyclerView.Adapter<DiscoveryAdapter.PokemonViewHolder>() {
    private var pokemonList: List<Pokemon?> = emptyList()

    /**
     *  Make use of associated binding variable from Pokemon item layout to access model class data
     *  Assign callback to each Pokemon list item to navigate to details screen
     */
    class PokemonViewHolder(
        private var binding: PokemonItemBinding,
        private val callback: DiscoveryListCallback
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.pokemon?.let { pokemon ->
                    callback.onClick(pokemon)
                }
            }
        }

        fun bind(pokemonListItem: Pokemon) {
            binding.pokemon = pokemonListItem
            binding.executePendingBindings()
        }
    }

    fun updateList(newList: List<Pokemon?>) {
        pokemonList = newList
        // Notify observers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        if (pokemon != null) {
            holder.bind(pokemon)
        }
    }

    override fun getItemCount(): Int = pokemonList.size
}