package com.itjuana.pokedex.ui.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.databinding.PokemonItemBinding
import com.itjuana.pokedex.ui.utils.PokemonListItemCallback
import com.itjuana.pokedex.ui.utils.PokemonListUpdate

class PokedexAdapter(private val callback: PokemonListItemCallback) :
    RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>(), PokemonListUpdate {
    private var pokemonList: List<Pokemon?> = emptyList()

    /**
     *  Make use of associated binding variable from Pokemon item layout to access model class data
     *  Assign callback to each Pokemon list item to navigate to details screen
     */
    class PokemonViewHolder(
        private var binding: PokemonItemBinding,
        private val callback: PokemonListItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {

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

    override fun updateList(newList: List<Pokemon?>) {
        pokemonList = newList
        // Notify observers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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