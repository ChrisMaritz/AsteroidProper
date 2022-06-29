package com.udacity.asteroidradar.data

import androidx.room.*

@Dao
interface AsteroidDatabaseDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroidData: AsteroidData)

    @Update
    suspend fun update(asteroidData: AsteroidData)

    @Query("SELECT * from asteroid_table WHERE id = :key")
    suspend fun get(key:Long):AsteroidData?

    @Query("DELETE FROM asteroid_table")
    suspend fun clear()


}
