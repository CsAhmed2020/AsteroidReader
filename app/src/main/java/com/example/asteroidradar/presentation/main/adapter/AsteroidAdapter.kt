package com.example.asteroidradar.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.databinding.AsteroidItemBinding
import com.example.asteroidradar.domain.model.Asteroid

class AsteroidAdapter(private val asteroidClick : AsteroidClick):
    ListAdapter<Asteroid,AsteroidsViewHolder>(AsteroidAdapterDiffCallback()) {

    private lateinit var binding:AsteroidItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            AsteroidsViewHolder.LAYOUT,parent,false)
        return AsteroidsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        holder.binding.also {
            it.asteroid = getItem(position)
            it.asteroidClick = asteroidClick
        }
    }

    //override fun getItemCount(): Int = asteroids.size

}

class AsteroidClick(val block: (Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = block(asteroid)
}



/**
 * class AsteroidAdapter(val asteroidClick : AsteroidClick):
RecyclerView.Adapter<AsteroidsViewHolder>() {

private lateinit var binding:AsteroidItemBinding

var asteroids: List<Asteroid> = emptyList()
set(value) {
field = value
notifyDataSetChanged()
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
AsteroidsViewHolder.LAYOUT,parent,false)
return AsteroidsViewHolder(binding)
}

override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
holder.binding.also {
it.asteroid = asteroids[position]
it.asteroidClick = asteroidClick
}
}

override fun getItemCount(): Int = asteroids.size

}
 */