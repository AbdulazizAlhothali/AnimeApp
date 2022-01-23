package com.example.animeapp.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.R
import com.example.animeapp.databinding.SearchFragmentBinding
import com.example.animeapp.ui.main.AnimeAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var searchVm: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= SearchFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.search)
        setHasOptionsMenu(true)
        binding.rvSearchAnime.layoutManager= GridLayoutManager(context,2)
        searchVm = ViewModelProvider(this)[SearchViewModel::class.java]
        if (searchVm.savedState.isNotEmpty()){
            loadAnimeImages(searchVm.savedState)
        }
    }

    private fun loadAnimeImages(searchKeyword: String){
        searchVm.searchAnime(searchKeyword).observe(requireActivity(), {
            binding.rvSearchAnime.adapter= AnimeAdapter(it.data,"SearchFragment")
            Log.d("Anime main response", it.data.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchIcon: MenuItem =  menu.findItem(R.id.app_bar_search)
        val searchView = searchIcon.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    loadAnimeImages(query.trim())
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}