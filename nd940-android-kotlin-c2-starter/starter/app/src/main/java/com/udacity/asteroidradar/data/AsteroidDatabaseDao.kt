package com.udacity.asteroidradar.data

import androidx.room.*
import retrofit2.http.GET

@Dao
interface AsteroidDatabaseDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroidData: AsteroidData)

    @Update
    suspend fun update(asteroidData: AsteroidData)

    @Query("SELECT * from asteroid_table ORDER by formatted_date")
    suspend fun get():MutableList<AsteroidData?>

    @Query("SELECT * from asteroid_table Where id == :id1")
    suspend fun getAsteroid(id1 : Int):AsteroidData

    @Query("DELETE FROM asteroid_table")
    suspend fun clear()


}

@Dao
interface ImageOfDayDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageOfDay: ImageOfDay)

    @Query("SELECT * from image_of_day")
    suspend fun get():ImageOfDay
}
