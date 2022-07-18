package com.udacity.asteroidradar.repository

import android.net.Network
import android.util.Log
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.AsteroidData
import com.udacity.asteroidradar.data.AsteroidDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsteroidRepository(private val database: AsteroidDatabase) {


    suspend fun refreshVideos() {
        val playlist = AsteroidApi.retrofitService.getProperties()
        AsteroidApi.retrofitService.getProperties().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val itsHere = parseAsteroidsJsonResult(JSONObject(response.body().toString()))

                for (items in itsHere){
                    var asteroidData = AsteroidData(
                        items.id,
                        items.absoluteMagnitude,
                        items.estimatedDiameter,
                        items.isPotentiallyHazardous,
                        items.relativeVelocity,
                        items.distanceFromEarth
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        database.asteroidDatabaseDao.insert(asteroidData)
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("API", "API failed")
            }

        })
    }

}