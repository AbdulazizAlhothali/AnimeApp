package com.example.animeapp.ui.animedetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.animeapp.R
import com.example.animeapp.databinding.AnimeDetailsFragmentBinding
import com.example.animeapp.databinding.SignUpFragmentBinding

class AnimeDetailsFragment : Fragment() {


    private lateinit var viewModel: AnimeDetailsViewModel
    val args: AnimeDetailsFragmentArgs by navArgs()
    private lateinit var binding: AnimeDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= AnimeDetailsFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleArg= args.detailsArg.title
        val posterArg= args.detailsArg.poster
        val descArg= args.detailsArg.desc
        val rateAgeArg= args.detailsArg.ageRate
        val rateArg = args.detailsArg.rate

        binding.tvTitleDetails.text= titleArg
        binding.ivPosterDetails.load(posterArg)
        binding.tvDescription.text= descArg
        binding.tvRateDeails.text= rateArg
        binding.tvAgeRate.text= rateAgeArg


    }



}