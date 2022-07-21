package com.udacity.asteroidradar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidData::class], version = 4,  exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroidDatabaseDao : AsteroidDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context):AsteroidDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
@Database(entities = [ImageOfDay::class], version = 1,  exportSchema = false)
abstract class ImageDatabase: RoomDatabase() {
    abstract val imageOfDayDao : ImageOfDayDao

    companion object {
        @Volatile
        private var INSTANCE2: ImageDatabase? = null

        fun getInstance(context: Context):ImageDatabase?{
            synchronized(this){
                var instance = INSTANCE2

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ImageDatabase::class.java,
                        "image_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE2 = instance
                }

                return instance
            }
        }
    }
}