package com.example.animeapp.ui.animedetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.animeapp.databinding.AnimeDetailsFragmentBinding
import kotlin.properties.Delegates

class AnimeDetailsFragment : Fragment() {


    private val args: AnimeDetailsFragmentArgs by navArgs()
    private lateinit var binding: AnimeDetailsFragmentBinding
    var x by Delegates.notNull<Float>()
    var y by Delegates.notNull<Float>()
    var dx by Delegates.notNull<Float>()
    var dy by Delegates.notNull<Float>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= AnimeDetailsFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleArg= args.detailsArg.title
        val posterArg= args.detailsArg.poster
        val descArg= args.detailsArg.desc
        val rateAgeArg= args.detailsArg.ageRate
        val rateArg = args.detailsArg.rate
        val epArg = args.detailsArg.episode

        binding.tvTitleDetails.text= titleArg
        binding.ivPosterDetails.load(posterArg)
        binding.tvDescription.text= descArg
        binding.tvRateDeails.text= rateArg
        binding.tvAgeRate.text= rateAgeArg
        binding.tvEpisode.text= epArg
        binding.ivShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm recommending you to watch $titleArg")
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }
        binding.tvTitleDetails.setOnTouchListener { view, motionEvent ->


            when(motionEvent.action){

                MotionEvent.ACTION_MOVE -> {
                    dx = motionEvent.x - x
                    dy = motionEvent.y - y

                    view.x = view.x + dx
                    view.y = view.y + dy
                }
                MotionEvent.ACTION_DOWN -> {
                    x = motionEvent.x
                    y = motionEvent.y
                }

            }

            true
        }


    }



}