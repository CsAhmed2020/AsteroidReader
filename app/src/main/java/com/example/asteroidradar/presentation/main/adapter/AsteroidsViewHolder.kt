package com.example.asteroidradar.presentation.main.adapter

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.AsteroidItemBinding
import com.example.asteroidradar.domain.model.Asteroid

class AsteroidsViewHolder(
     val binding: AsteroidItemBinding
):RecyclerView.ViewHolder(binding.root) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.asteroid_item
    }
}
