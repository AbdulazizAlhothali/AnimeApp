package com.example.animeapp.ui.signup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeapp.databinding.SignUpFragmentBinding

class SignUpFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: SignUpFragmentBinding
    private val signUpVM by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
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
        signUpVM.navigateScreen.observe(viewLifecycleOwner,{
          it.getContentIfNotHandled()?.let { action->
              navController.navigate(action)
          }
        })
        signUpVM.message.observe(viewLifecycleOwner,{
            it.getContentIfNotHandled()?.let { message->
                Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}