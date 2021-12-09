package com.example.animeapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.animeapp.R
import com.example.animeapp.databinding.SignUpFragmentBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: SignUpFragmentBinding

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= SignUpFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //this.activity?.actionBar?.hide()

        //
        val signUpVM= ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.lifecycleOwner= this
        binding.signUpViewModel= signUpVM

        binding.btnGo.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_animeFragment)
        }


        /*navController = Navigation.findNavController(view)
        signUpVM.navigateScreen.observe(requireActivity(), Observer {
          it.getContentIfNotHandled()?.let { action->
              navController.navigate(action)
          }
        })*/
        signUpVM.message.observe(requireActivity(), Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()
            }
        })

    }//end of viewCreated

}