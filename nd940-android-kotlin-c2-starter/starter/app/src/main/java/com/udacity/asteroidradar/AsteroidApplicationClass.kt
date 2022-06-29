package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshAsteroidData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplicationClass: Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        delayedUnit()
        super.onCreate()
    }

    private fun delayedUnit() = applicationScope.launch {
        setUpRecurringWork()
    }

    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<RefreshAsteroidData>()
                .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshAsteroidData>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        val db = applicationContext.getDatabasePath("asteroid_database")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteroidData.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
            Log.i("database", "ran")
        if(db.exists() == false){
            WorkManager
                .getInstance()
                .enqueue(uploadWorkRequest)

            Log.i("database", db.toString())
        }
    }
}