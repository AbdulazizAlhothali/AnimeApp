package com.example.animeapp.ui.favorite



import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.AnimeApp
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.FavoriteFragmentBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import com.example.animeapp.R


class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var favList: MutableList<Favorite>
    lateinit var favAdapter : FavoriteAdapter
    private lateinit var favVM : FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FavoriteFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.favorite)
        favList = mutableListOf()
        binding.rvFavAnime.layoutManager = GridLayoutManager(context,1)

        favVM = FavoriteViewModelFactory((requireActivity().application as AnimeApp).favRepo).create(FavoriteViewModel::class.java)

        favVM.showMyFavAnime2("Favorite",favList).observe(viewLifecycleOwner) {
            favAdapter = FavoriteAdapter(it)
            binding.rvFavAnime.adapter = favAdapter
        }

        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(binding.rvFavAnime)
    }

    private var simpleCallback= object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val deletedFav = favList[position]
            when(direction){
                ItemTouchHelper.LEFT -> {
                    favVM.delete(deletedFav)
                }
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(binding.root.resources.getColor(R.color.red,binding.root.resources.newTheme()))
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}