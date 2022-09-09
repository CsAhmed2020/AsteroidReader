package com.example.asteroidradar.presentation.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.MainFragmentBinding
import com.example.asteroidradar.presentation.main.adapter.AsteroidAdapter
import com.example.asteroidradar.presentation.main.adapter.AsteroidClick

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        val viewModelFactory = ViewModelFactory(activity.application)
        ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
    }

    private var asteroidAdapter: AsteroidAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        asteroidAdapter = AsteroidAdapter(AsteroidClick {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })

        binding.asteroidRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = asteroidAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroidAdapter!!.submitList(asteroids)
            Log.d("Ahmed",asteroids.toString())
        })

        viewModel.asteroidsFromToday.observe(viewLifecycleOwner, Observer {
            Log.d("Ahmed10",it.toString())
        })

        viewModel.todayAsteroids.observe(viewLifecycleOwner, Observer {
            Log.d("Ahmed20",it.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_all_menu -> {
                asteroidAdapter!!.submitList(viewModel.asteroidsFromToday.value!!)
            }
            R.id.show_rent_menu -> {
                asteroidAdapter!!.submitList(viewModel.todayAsteroids.value!!)
            }
            R.id.show_buy_menu -> {
                asteroidAdapter!!.submitList(viewModel.asteroids.value!!)
        }
        }
        return true
    }



}
