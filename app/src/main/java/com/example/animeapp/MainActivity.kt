package com.example.animeapp

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.show()

        val sharedPreferencesSettings = this.getSharedPreferences(Utils.SETTINGS, Activity.MODE_PRIVATE)
        val language = sharedPreferencesSettings.getString(Utils.LANGUAGE, "")
        if (language.toString() == "ar") {
            localization("ar")
        } else {
            localization("en")
        }
        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.buttonView)
            navView.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.signUpFragment -> {
                        navView.visibility = View.GONE
                    }
                    R.id.signInFragment -> {
                        navView.visibility = View.GONE
                    }
                    R.id.forgotPasswordFragment -> {
                        navView.visibility = View.GONE
                    }
                    else -> {
                        navView.visibility = View.VISIBLE
                    }
                }
            }
    }


    private fun localization(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources?.updateConfiguration(config, this.resources.displayMetrics)
    }
}