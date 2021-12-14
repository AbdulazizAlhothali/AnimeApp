package com.example.animeapp.favorite

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.FavoriteFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favList: ArrayList<Favorite>
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    lateinit var favListAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FavoriteFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favList= arrayListOf()
        binding.rvFavAnime.layoutManager = GridLayoutManager(context,1)
        favListAdapter = FavoriteAdapter(favList)
        binding.rvFavAnime.adapter= favListAdapter


        showMyFavAnime()

        binding.btnDelete.setOnClickListener{




            delete().notifyDataSetChanged()

            //favListAdapter.notifyDataSetChanged()

            /** Swap*/


        }

    }
    private fun showMyFavAnime() {

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser).collection("Favorite").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null) {
                    Log.e("Firestore", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        favList.add(dc.document.toObject(Favorite::class.java))
                    }
                }
                favListAdapter.notifyDataSetChanged()
            }

        })
    }

    private fun showChangeLanguage(type:String){
        val mBuilder = AlertDialog.Builder(this.requireContext())
        mBuilder.setTitle("Choose Language")
        mBuilder.setMessage(type)
        val mDialog =mBuilder.create()
        mDialog.show()
    }

    private fun delete(): FavoriteAdapter {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser).collection("Favorite").document("Cowboy Bebop").delete()


        return favListAdapter
    }

}