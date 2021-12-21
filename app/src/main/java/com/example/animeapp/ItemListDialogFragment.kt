package com.example.animeapp

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.animeapp.databinding.FragmentItemListDialogListDialogBinding


class ItemListDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentItemListDialogListDialogBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}