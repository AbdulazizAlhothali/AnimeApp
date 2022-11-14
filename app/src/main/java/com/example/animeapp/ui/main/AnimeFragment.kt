package com.example.animeapp.ui.main


import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.databinding.AnimeFragmentBinding

class AnimeFragment : Fragment() {

    private lateinit var binding: AnimeFragmentBinding
    private lateinit var animeVm: AnimeViewModel
    private var i = 0
    private var j = 1
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


        loadAnimeImages()
        check()
        binding.tvPageNum.text = "$j"
        binding.btnNext.setOnClickListener {
            if (i >= 0){
                i += 20
                j++
                binding.tvPageNum.text = "$j"
                loadAnimeImages("$i")

                binding.btnPreviouse.isEnabled= true
                binding.btnPreviouse.isVisible= true
            }
        }

            binding.btnPreviouse.setOnClickListener {
                check()
                if (i>=20){
                    i-=20
                    j--
                    binding.tvPageNum.text = "$j"
                    loadAnimeImages("$i")
                }
                if (i==0){
                    binding.btnPreviouse.isEnabled= false
                    binding.btnPreviouse.isVisible= false
                }
            }
    }


    private fun check (){
        if (i<20){
            binding.btnPreviouse.isEnabled= false
            binding.btnPreviouse.isVisible= false
        } else if (i>=20){
            binding.btnPreviouse.isEnabled= true
            binding.btnPreviouse.isVisible= true
        }
    }

    private fun loadAnimeImages(pageNum:String= "0"){
        animeVm.allAnime(pageNum).observe(requireActivity()) {
            binding.rvAnime.adapter = AnimeAdapter(it.data, "AnimeFragment")
            Log.d("Anime main response", it.toString())
        }
    }
}