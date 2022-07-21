package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.ImageApi
import com.udacity.asteroidradar.api.getImageOfTheDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.AsteroidData
import com.udacity.asteroidradar.data.AsteroidDatabase
import com.udacity.asteroidradar.data.ImageDatabase
import com.udacity.asteroidradar.data.ImageOfDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RefreshAsteroidData(appContext: Context,
                          params: WorkerParameters, ):
    CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "RefreshAsteroidWorker"
    }

    override suspend fun doWork(): Result {

        var database = AsteroidDatabase.getInstance(applicationContext)
        var asteroidDatabaseDao = database.asteroidDatabaseDao
        return try{
            AsteroidApi.retrofitService.getProperties().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val itsHere = parseAsteroidsJsonResult(JSONObject(response.body().toString()))

                    for (items in itsHere){
                        var asteroidData = AsteroidData(
                            items.id,
                            items.codename,
                            items.closeApproachDate,
                            items.absoluteMagnitude,
                            items.estimatedDiameter,
                            items.isPotentiallyHazardous,
                            items.relativeVelocity,
                            items.distanceFromEarth,

                            )

                        CoroutineScope(Dispatchers.IO).launch {
                            asteroidDatabaseDao.insert(asteroidData)
                            Log.i("Data", "ran")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("Database Failed", "The database has failed")
                }

            })

            var databaseImage = ImageDatabase.getInstance(applicationContext)
            var imageDatabaseDao = databaseImage?.imageOfDayDao

            ImageApi.retrofitService2.getImage().enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val response2 = response.body().toString()

                    val listImageInfo = getImageOfTheDay(JSONObject(response2))

                    val data = ImageOfDay(
                        1 ,listImageInfo[2], listImageInfo[1], listImageInfo[0]
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        imageDatabaseDao?.insert(data)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("Database Failed", "The database has failed")
                }

            })

            Result.success()
        }catch (e: Exception){
            Result.retry()
        }
    }
}