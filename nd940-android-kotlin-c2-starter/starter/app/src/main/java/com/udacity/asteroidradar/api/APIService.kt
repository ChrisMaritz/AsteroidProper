package com.udacity.asteroidradar.api

import android.icu.text.SimpleDateFormat
import android.media.Image
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"
val BASE_URL2 = "https://api.nasa.gov/planetary/"

    private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

    private val retrofit2 = Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

interface AsteroidApiService {

    @GET("feed?api_key=dbfpraEVQSdqX6Iy1H0zoQcAwtNUTSxqf7IUGlpz")
    fun getProperties():
            Call<String>
}

interface ImageApiService {
    @GET("apod?api_key=dbfpraEVQSdqX6Iy1H0zoQcAwtNUTSxqf7IUGlpz")
    fun getImage():
            Call<String>
}

object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}

object ImageApi {
    val retrofitService2 : ImageApiService by lazy {
        retrofit2.create(ImageApiService::class.java)
    }
}