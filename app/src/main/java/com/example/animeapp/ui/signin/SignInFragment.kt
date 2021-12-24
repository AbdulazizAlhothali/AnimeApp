package com.example.animeapp.ui.signin


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeapp.databinding.SignInFragmentBinding

class SignInFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: SignInFragmentBinding
    private val signInVM by lazy {
        ViewModelProvider(this).get(SignInViewModel::class.java)
    }


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

    }



}