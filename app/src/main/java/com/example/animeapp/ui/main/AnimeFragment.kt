package com.example.animeapp.ui.main


import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.databinding.AnimeFragmentBinding

class AnimeFragment : Fragment() {

    private lateinit var binding: AnimeFragmentBinding
    private lateinit var animeVm: AnimeViewModel

    private var i = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= AnimeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.rvAnime.layoutManager= GridLayoutManager(context,2)
        animeVm = ViewModelProvider(this)[AnimeViewModel::class.java]
        //animeVm.myNotification(MainActivity())

        loadAnimeImages()
        check()

        binding.btnNext.setOnClickListener {
            if (i >= 0){
                i += 20
                loadAnimeImages("$i")

                binding.btnPreviouse.isEnabled= true
            }
        }

            binding.btnPreviouse.setOnClickListener {
                check()
                if (i>=20){
                    i-=20
                    loadAnimeImages("$i")
                }
                if (i==0){
                    binding.btnPreviouse.isEnabled= false
                }

            }
    }

    private fun check (){
        if (i<20){
            binding.btnPreviouse.isEnabled= false
        } else if (i>=20){
            binding.btnPreviouse.isEnabled= true
        }
    }

    private fun loadAnimeImages(pageNum:String= "0"){
        animeVm.allAnime(pageNum).observe(requireActivity(), {
                binding.rvAnime.adapter= AnimeAdapter(it.data,"AnimeFragment")
            Log.d("Anime main response", it.toString())
        })
    }
}