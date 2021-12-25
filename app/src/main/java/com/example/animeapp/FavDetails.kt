package com.example.animeapp

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.animeapp.databinding.FavDetailsBinding
import com.example.animeapp.ui.animedetail.AnimeDetailsFragmentArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FavDetails : BottomSheetDialogFragment() {

    private lateinit var binding: FavDetailsBinding
    val args: FavDetailsArgs by navArgs()
    private var i = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FavDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        check()

        binding.tvTitleDetails.text = args.animeTitle
        binding.tvEpNum.text = args.animeEp
        binding.rbRateFavAnime.rating = args.animeRate.toFloat()
        binding.ivNextEp.setOnClickListener {
            if (i >= 0){
                i += 1
                binding.tvEpNum.text = i.toString()
                binding.ivPreviousEp.isEnabled= true
            }
        }

        binding.ivPreviousEp.setOnClickListener {
            check()
            if (i>=1){
                i-=1
                binding.tvEpNum.text = i.toString()
            }
            if (i==0){
                binding.ivPreviousEp.isEnabled= false
            }
        }

        binding.btnFavSave.setOnClickListener {
            val uId = FirebaseAuth.getInstance().currentUser?.uid
            val upDateUserData = Firebase.firestore.collection("users")
            upDateUserData.document(uId.toString()).collection("Favorite").document(args.animeTitle).update("episode", " "+binding.tvEpNum.text.toString(),
                "rate", " "+binding.rbRateFavAnime.rating.toString())
            val action = FavDetailsDirections.actionFavDetailsToFavoriteFragment()
            findNavController().navigate(action)
        }
    }

    private fun check (){
        if (i<1){
            binding.ivPreviousEp.isEnabled= false
        } else if (i>=1){
            binding.ivPreviousEp.isEnabled= true
        }
    }

}