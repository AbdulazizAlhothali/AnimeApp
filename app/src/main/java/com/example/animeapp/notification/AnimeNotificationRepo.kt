package com.example.animeapp.notification


import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.animeapp.MainActivity
import java.util.concurrent.TimeUnit

class AnimeNotificationRepo () {
    private val list = listOf("Explore New Anime","Check The Latest Trending Anime","You can Track Episode You Watch with Us").random()
    fun myNotification(mainActivity: MainActivity){
        val myWorkRequest= PeriodicWorkRequest
            .Builder(AnimeWorker::class.java,12,TimeUnit.HOURS)
            .setInputData(workDataOf(
                "title" to "Anime App",
                "message" to list)
            )
            .build()
        WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
            "periodicStockWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            myWorkRequest
        )
    }
}