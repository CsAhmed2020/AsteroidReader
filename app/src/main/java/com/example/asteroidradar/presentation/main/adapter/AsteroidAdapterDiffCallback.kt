package com.example.asteroidradar.presentation.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.asteroidradar.domain.model.Asteroid

class AsteroidAdapterDiffCallback : DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }


}
