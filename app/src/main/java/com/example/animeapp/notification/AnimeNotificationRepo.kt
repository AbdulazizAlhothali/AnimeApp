package com.example.animeapp.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.animeapp.MainActivity
import com.example.animeapp.ui.main.AnimeFragment
import java.util.concurrent.TimeUnit

class AnimeNotificationRepo () {
    private val list = listOf("a","b","c").random()
    fun myNotification(mainActivity: MainActivity){
        val myWorkRequest= PeriodicWorkRequest
            .Builder(AnimeWorker::class.java,15,TimeUnit.MINUTES)
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