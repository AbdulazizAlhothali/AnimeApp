package com.example.animeapp.ui.signin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.animeapp.R
import com.example.animeapp.databinding.SignInFragmentBinding
import com.example.animeapp.databinding.SignUpFragmentBinding
import com.example.animeapp.ui.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: SignInFragmentBinding
    private val signInVM by lazy {
        ViewModelProvider(this).get(SignInViewModel::class.java)
    }
    private lateinit var go_toRegisterPage: TextView
    //private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= SignInFragmentBinding.inflate(inflater,container, false)
        return binding.root

    }
    /*private fun retrieveData(){

        val myPref = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val name = myPref.getString("username", "")
        username_email.setText(name)

    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner= this
        binding.signInVm= signInVM
        navController = Navigation.findNavController(view)
        signInVM.navigateScreen.observe(requireActivity(), Observer {
            it.getContentIfNotHandled()?.let { action->
                navController.navigate(action)
            }
        })
        signInVM.message.observe(requireActivity(), Observer {
            it.getContentIfNotHandled()?.let { toast ->
                Toast.makeText(requireContext(),toast, Toast.LENGTH_SHORT).show()
            }
        })

        //showing username name or email
       // retrieveData()
        // session management
        /*val currentuser = auth.currentUser
        if (currentuser != null) {
            val action = SignInFragmentDirections.actionSignInFragmentToAnimeFragment()
            findNavController().navigate(action)
        }*/



        /*go_toRegisterPage.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }*/
    }



}