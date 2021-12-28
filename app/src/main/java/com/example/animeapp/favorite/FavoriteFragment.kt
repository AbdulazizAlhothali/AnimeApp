package com.example.animeapp.favorite


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.FavoriteFragmentBinding


class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var favList: MutableList<Favorite>

    private val viewModel by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
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
        favList = mutableListOf()
        binding.rvFavAnime.layoutManager = GridLayoutManager(context,1)
        viewModel.showMyFavAnime(favList).observe(viewLifecycleOwner,{
            binding.rvFavAnime.adapter= FavoriteAdapter(favList)

        })

        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(binding.rvFavAnime)
    }

    private var simpleCallback= object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
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
                    viewModel.delete(deletedFav)
                    favList.remove(deletedFav)
                    FavoriteAdapter(favList).notifyItemRemoved(position)

                }

            }

        }
    }
}