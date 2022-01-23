package com.example.animeapp.ui.favorite



import android.annotation.SuppressLint
import android.graphics.Canvas
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.FavoriteFragmentBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import com.example.animeapp.R


class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var favList: MutableList<Favorite>
    lateinit var favAdapter : FavoriteAdapter
    private val favVM by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

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
        favAdapter = FavoriteAdapter(favList)
        binding.rvFavAnime.layoutManager = GridLayoutManager(context,1)
        favVM.showMyFavAnime(favList,viewLifecycleOwner).observe(viewLifecycleOwner,{
            binding.rvFavAnime.adapter= favAdapter
            favAdapter.notifyDataSetChanged()
        })

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
                    favList.remove(deletedFav)
                    favAdapter.notifyItemRemoved(position)
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