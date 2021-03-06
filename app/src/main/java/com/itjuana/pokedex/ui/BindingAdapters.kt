package com.itjuana.pokedex.ui

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.itjuana.pokedex.R
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.model.Type
import com.itjuana.pokedex.ui.utils.PokemonListUpdate
import com.itjuana.pokedex.ui.utils.Status

/**
 * Bind pokemon sprite to imageView
 */
@BindingAdapter("spriteUrl")
fun bindSprite(imgView: ImageView, imgUrl: String?) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .circleCrop()
        .into(imgView)
}

/**
 * Update RecyclerView using interface, providing support for database and API adapters
 */
@BindingAdapter("pokemonList")
fun bindPokemon(recyclerView: RecyclerView, pokemonList: List<Pokemon?>?) {
    if (pokemonList != null) {
        val adapter = recyclerView.adapter as PokemonListUpdate
        adapter.updateList(pokemonList)
    }
}

/**
 * Set Pokemon type text
 */
@BindingAdapter("pokemonTypeText")
fun bindText(textView: TextView, type: Type) {
    textView.text = Type.fromInt(type.id).name
}

/**
 * Set list item background color depending on Pokemon type
 */
@BindingAdapter("pokemonItemBackgroundColor")
fun bindBackgroundColor(view: View, type: Type) {
    val typeColors = view.context.resources.getIntArray(R.array.pokemon_types)
    view.setBackgroundColor(typeColors[type.id - 1])
}

/**
 * Show placeholder image based on search status, via a mutable data in SearchViewModel
 */
@BindingAdapter("pokemonSearchNoResults")
fun bindStatusImage(statusView: LinearLayout, status: Status?) {
    statusView.visibility = when (status) {
        Status.ERROR, Status.EMPTY -> {
            View.VISIBLE
        }
        else -> {
            View.GONE
        }
    }
}