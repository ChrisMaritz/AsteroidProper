package com.udacity.asteroidradar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_table")
data class AsteroidData(

    @PrimaryKey(autoGenerate = false)
    var id : Long,

    @ColumnInfo(name = "code_name")
    var codeName : String,

    @ColumnInfo(name = "formatted_date")
    var formattedDate : String,

    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter_max")
    var estimatedDiameterMax : Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    var PotentialHazard : Boolean,

    @ColumnInfo(name = "kilometers_per_second")
    var kmPerSecond : Double,

    @ColumnInfo(name = "astro_miss_distance")
    var astroMissDistance : Double,

    )

@Entity(tableName = "image_of_day")
data class ImageOfDay(
    @PrimaryKey
    var id : Long,

    @ColumnInfo(name = "title")
    var title : String,

    @ColumnInfo(name = "media_type")
    var mediaType : String,

    @ColumnInfo(name = "image")
    var image : String,

)