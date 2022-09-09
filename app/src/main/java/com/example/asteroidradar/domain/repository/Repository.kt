package com.example.asteroidradar.domain.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.data.database.*
import com.example.asteroidradar.data.network.*
import com.example.asteroidradar.domain.model.Asteroid
import com.example.asteroidradar.domain.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class Repository(private val database: AsteroidDatabase) {

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.asteroidDao.getPictureOfDay()) {podEntity ->
            podEntity?.asPODModel()
        }

    //filter week
    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) { asteroidEntities ->
            asteroidEntities?.asAsteroidList()
        }

    //filter today
    val todayAsteroids:LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getTodayAsteroids(getEndDate())){ todayAsteroids ->
            todayAsteroids?.asAsteroidList()
        }


    //filter saved
    val asteroidsFromToday:LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroidsFromToday(getEndDate())){ weekAsteroids ->
            weekAsteroids?.asAsteroidList()
        }


    suspend fun refreshPicture() {
        withContext(Dispatchers.IO) {
            val todayPODinDatabase = database.asteroidDao.checkExistedPod(getEndDate())
            if (todayPODinDatabase == 0) {
                val response = Network.asteroidService.getPictureOfDay()
                if (response.isSuccessful) {
                    response.body()?.let {
                        database.asteroidDao.savePictureOfDay(it.asPODEntity())
                    }
                }
            }
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
                val todayAsteroidsInDb =
                    database.asteroidDao.checkExistedAsteroids(getEndDate())
                if (todayAsteroidsInDb == 0) {
                    val response = Network.asteroidService.getAsteroids(getStartDate(), getEndDate())
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val data = parseAsteroidsJsonResult(JSONObject(it))
                            database.asteroidDao.saveAsteroids(*data.asAsteroidEntityList())
                        }
                    }
                }

        }
    }


    suspend fun clearOldData(){
        withContext(Dispatchers.IO){
            database.asteroidDao.deletePOD(getEndDate())
            database.asteroidDao.deleteAsteroids(getEndDate())
        }
    }
}
