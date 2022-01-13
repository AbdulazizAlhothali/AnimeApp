package com.example.animeapp.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val email = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage

    fun forgotPass(){
        if (email.value.isNullOrEmpty()) {
            statusMessage.value = "Please enter your E-mail"
        } else {
            auth.sendPasswordResetEmail(email.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        statusMessage.value = "E-mail send successful to reset your password"
                    } else {
                        statusMessage.value = "The email wasn't correct"
                    }
                }
        }

    }
}