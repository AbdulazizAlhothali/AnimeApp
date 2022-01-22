package com.example.animeapp.ui.signup

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeapp.R
import com.example.animeapp.Utils
import com.example.animeapp.databinding.SignUpFragmentBinding
import com.example.animeapp.ui.signin.SignInFragmentDirections
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var navController: NavController
    private lateinit var binding: SignUpFragmentBinding
    private val signUpVM by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
    }
    private val settings by lazy {
        this.requireActivity().getSharedPreferences(Utils.SETTINGS, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= SignUpFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner= this
        binding.signUpViewModel= signUpVM
        navController = Navigation.findNavController(view)
        signUpVM.apply {
            navigateScreen.observe(viewLifecycleOwner,{ action->
                    navController.navigate(action)
            })
            message.observe(viewLifecycleOwner, { message ->
                    if (message == Utils.PASSWORD_MISMATCH){
                        Toast.makeText(context,getString(R.string.pass_mismatch),Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                    }
            })
        }

        Utils.message.observe(viewLifecycleOwner,{ message ->
                when (message) {
                    Utils.FIELDS_MUST_BE_FILLED -> {
                        Toast.makeText(context,getString(R.string.fields_must_be_filled), Toast.LENGTH_SHORT).show()
                    }
                    Utils.PASSWORD_COUNT -> {
                        Toast.makeText(context,getString(R.string.pass_more_than_8), Toast.LENGTH_SHORT).show()
                    }
                    Utils.WRONG_EMAIL -> {
                        Toast.makeText(context,getString(R.string.wrong_email), Toast.LENGTH_SHORT).show()
                    }
                }
        })

        val remembered = settings.getBoolean("remember",false)
        binding.cbRememberMe.setOnClickListener {

            if (binding.cbRememberMe.isChecked) {
                settings.edit()
                    .putBoolean("remember", true)
                    .apply()
            } else {
                settings.edit()
                    .putBoolean("remember", false)
                    .apply()
            }
        }

        val currentUser = auth.currentUser

        binding.cbRememberMe.isChecked = remembered
        if (binding.cbRememberMe.isChecked && currentUser != null){
            val action = SignInFragmentDirections.actionSignInFragmentToMainFragment()
            navController.navigate(action)
        }
    }
}