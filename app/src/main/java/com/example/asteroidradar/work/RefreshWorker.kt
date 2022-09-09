package com.example.asteroidradar.work

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.data.database.AsteroidDatabase
import com.example.asteroidradar.domain.repository.Repository

class RefreshWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORKER_NAME = "Refresh Worker"
    }
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = Repository(database)
        return try {
            repository.refreshPicture()
            repository.refreshAsteroids()
            repository.clearOldData()
            Result.Success()

        }catch (e:Exception){
            Result.retry()
        }

    }
}