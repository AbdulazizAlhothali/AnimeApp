package com.example.animeapp.ui.main

import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.R
import com.example.animeapp.databinding.AnimeFragmentBinding

class AnimeFragment : Fragment() {

    private lateinit var binding: AnimeFragmentBinding

    companion object {
        fun newInstance() = AnimeFragment()
    }

    private lateinit var animeVm: AnimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= AnimeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity?.actionBar?.hide()

        binding.rvAnime.layoutManager= LinearLayoutManager(context/*, LinearLayoutManager.HORIZONTAL, false*/)
        animeVm = ViewModelProvider(this)[AnimeViewModel::class.java]

        loadAnimeImages()



    }

    private fun loadAnimeImages(query: String? = null){
        animeVm.anime(query).observe(requireActivity(), {
            if (query.isNullOrEmpty()){
                binding.rvAnime.adapter= AnimeAdapter(it.data)
            } else {
                binding.rvAnime.swapAdapter(AnimeAdapter(it.data),false)
            }
            Log.d("Anime main response", it.data.toString())
        })
    }

}