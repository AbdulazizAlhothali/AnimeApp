package com.example.animeapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.databinding.TrendingAnimeFragmentBinding

class TrendingAnimeFragment : Fragment() {

    private lateinit var binding: TrendingAnimeFragmentBinding

    private lateinit var trendingAnimeVM: TrendingAnimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= TrendingAnimeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trendingRV.layoutManager = GridLayoutManager(context,2)
        trendingAnimeVM = ViewModelProvider(this)[TrendingAnimeViewModel::class.java]
        loadAnimeImages()
    }

    private fun loadAnimeImages(){

        trendingAnimeVM.trendingAnime().observe(viewLifecycleOwner) {
            binding.trendingRV.adapter = AnimeAdapter(it.data, "AnimeFragment")
            Log.d("Trending Anime main response", it.toString())
        }
    }

}