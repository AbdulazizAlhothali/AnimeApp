package com.example.animeapp.ui.forgotpassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animeapp.R
import com.example.animeapp.databinding.ForgotPasswordFragmentBinding

class ForgotPasswordFragment : Fragment() {


    lateinit var binding: ForgotPasswordFragmentBinding
    private lateinit var forgotPassVM: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= ForgotPasswordFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.forgot_password)
        forgotPassVM = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.lifecycleOwner = this
        binding.forgetPassVm = forgotPassVM
        forgotPassVM.message.observe(viewLifecycleOwner,{toast ->
                Toast.makeText(context,toast,Toast.LENGTH_SHORT).show()
        })
    }
}