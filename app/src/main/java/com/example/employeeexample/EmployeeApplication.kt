package com.example.employeeexample

import android.app.Application
import androidx.work.*
import com.example.employeeexample.data.EmployeeOfTheDayWorker
import java.util.concurrent.TimeUnit


class EmployeeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        //minimum duration 15 minutes
        val myWork =  PeriodicWorkRequest.Builder(EmployeeOfTheDayWorker::class.java,
            16, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "EmployeeOfTheDay",
        ExistingPeriodicWorkPolicy.REPLACE,
        myWork)
    }
}