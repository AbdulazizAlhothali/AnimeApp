package com.example.animeapp.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.example.animeapp.MainActivity
import com.example.animeapp.R
import com.example.animeapp.Utils
import java.util.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)

        val sharedPreferencesSettings = this.getSharedPreferences(Utils.SETTINGS, Activity.MODE_PRIVATE)
        val language = sharedPreferencesSettings.getString(Utils.LANGUAGE, "")
        val darkMode = sharedPreferencesSettings.getBoolean(Utils.CHECK,false)

        if (language.toString() == "ar") {
            setLocate()
        }
        if (darkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4500)
    }

    private fun setLocate() {
        val locale = Locale("ar")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources?.updateConfiguration(config, this.resources.displayMetrics)

    }

}