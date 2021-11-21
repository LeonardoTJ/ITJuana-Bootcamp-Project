package com.itjuana.pokedex.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import com.itjuana.pokedex.R
import com.itjuana.pokedex.data.domain.model.Pokemon
import com.itjuana.pokedex.data.remote.model.Type
import com.itjuana.pokedex.ui.utils.PokemonAdapter
import com.itjuana.pokedex.util.Status
import java.util.*

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
 * Update RecyclerView
 */
@BindingAdapter("pokemonList")
fun bindPokemon(recyclerView: RecyclerView, pokemonList: List<Pokemon?>?) {
    if (pokemonList != null) {
        val adapter = recyclerView.adapter as PokemonAdapter
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
    view.setBackgroundColor(typeColors[type.id-1])
}

/**
 * Show card view with pokemon data based on search status, via a mutable data in SearchViewModel
 */
//@BindingAdapter("pokemonSearchCard")
//fun bindResultCard(pokemonCard: MaterialCardView, status: Status?) {
//    when (status) {
//        Status.LOADING, Status.ERROR, Status.EMPTY -> {
//            pokemonCard.visibility = View.GONE
//        }
//        Status.SUCCESS -> {
//            pokemonCard.visibility = View.VISIBLE
//        }
//    }
//}
/**
 * Show placeholder image based on search status, via a mutable data in SearchViewModel
 */
@BindingAdapter("pokemonSearchNoResults")
fun bindStatusImage(statusImageView: ImageView, status: Status?) {
    when (status) {
        Status.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_image)
        }
        Status.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        Status.SUCCESS -> {
            statusImageView.visibility = View.GONE
        }
        Status.EMPTY -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_image_missing)
        }
    }
}