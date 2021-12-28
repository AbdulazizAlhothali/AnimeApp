package com.example.animeapp.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeapp.R
import com.example.animeapp.databinding.SearchFragmentBinding

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
        setHasOptionsMenu(true)
        binding.rvSearchAnime.layoutManager= LinearLayoutManager(context/*, LinearLayoutManager.HORIZONTAL, false*/)
        searchVm = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    private fun loadAnimeImages(searchKeyword: String){
        searchVm.searchAnime(searchKeyword).observe(requireActivity(), {
            binding.rvSearchAnime.adapter= SearchAnimeAdapter(it.data)
            Log.d("Anime main response", it.data.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val TAG = "searchView"
        val searchIcon: MenuItem =  menu.findItem(R.id.app_bar_search)
        val searchView = searchIcon.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $query")
                    loadAnimeImages(query.trim())
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $newText")
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}