package com.example.animeapp.ui.main

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) :
    FragmentStateAdapter(fm, lifecycle){
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {

                return AnimeFragment()
            }
            1 -> {

                return TrendingAnimeFragment()
            }
            else -> return AnimeFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }

}
