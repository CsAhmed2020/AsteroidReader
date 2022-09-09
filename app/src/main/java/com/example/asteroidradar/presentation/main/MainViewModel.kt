package com.example.asteroidradar.presentation.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.asteroidradar.data.database.AsteroidDatabase
import com.example.asteroidradar.data.network.checkInternetConnection
import com.example.asteroidradar.domain.model.Asteroid
import com.example.asteroidradar.domain.model.PictureOfDay
import com.example.asteroidradar.domain.repository.Repository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = Repository(database)

    val pictureOfDay: LiveData<PictureOfDay>
        get() = repository.pictureOfDay

    //private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>>
        get() = repository.asteroidList

    val todayAsteroids:LiveData<List<Asteroid>>
        get() = repository.todayAsteroids

    val asteroidsFromToday:LiveData<List<Asteroid>>
        get() = repository.asteroidsFromToday

    init {
        viewModelScope.launch {
            if (checkInternetConnection(application)) {
                viewModelScope.launch {
                    repository.refreshAsteroids()
                    repository.refreshPicture()
                }
            }else {
                Toast.makeText(application.applicationContext,"Connect to internet to get live data",Toast.LENGTH_LONG).show()
            }
        }
    }

}
class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}