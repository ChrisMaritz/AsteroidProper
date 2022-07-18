package com.udacity.asteroidradar.api

import android.util.Log
import org.json.JSONObject

fun getImageOfTheDay(jsonResult: JSONObject): List<String>{
    val image = jsonResult.get("url").toString()
    val mediaType = jsonResult.get("media_type").toString()
    val title = jsonResult.get("title").toString()

    return listOf<String>(image, mediaType, title)
}