package com.example.animeapp.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.animeapp.R
import com.example.animeapp.databinding.MainFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberOfTabs = 2
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.main)

        // make text and icon of tab in the same line
        binding.tabLayout.isInlineLabel = true

        // Set the ViewPager Adapter
        val adapter = TabsPagerAdapter(childFragmentManager, lifecycle, numberOfTabs)
        binding.tabsViewpager.adapter = adapter

        // Enable Swipe, (default is true)
        binding.tabsViewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.all_anime)
                }
                1 -> {
                    tab.text = getString(R.string.trending_anime)
                }
            }
        }.attach()
    }

}