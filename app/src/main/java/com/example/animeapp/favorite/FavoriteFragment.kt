package com.example.animeapp.favorite

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.FavoriteFragmentBinding
import com.example.animeapp.ui.signin.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.text.SimpleDateFormat

class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var favList: List<Favorite>
    private val viewModel by lazy {
        ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FavoriteFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.rvFavAnime.layoutManager = GridLayoutManager(context,1)
        viewModel.showMyFavAnime().observe(viewLifecycleOwner,{
            binding.rvFavAnime.adapter= FavoriteAdapter(it)
            favList = it
        })






        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(binding.rvFavAnime)
    }

    private var simpleCallback= object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val deletedFav = favList[position]

            when(direction){
                ItemTouchHelper.LEFT -> {
                    viewModel.delete(deletedFav).observe(viewLifecycleOwner,{
                        viewModel.showMyFavAnime().observe(viewLifecycleOwner,{
                            binding.rvFavAnime.adapter= FavoriteAdapter(it)
                            favList= it
                        })
                    })

                }

                ItemTouchHelper.RIGHT -> {
                    TODO()
                }
            }

        }
    }
}