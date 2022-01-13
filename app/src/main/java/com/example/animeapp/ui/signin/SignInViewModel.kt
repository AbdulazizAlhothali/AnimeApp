package com.example.animeapp.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.animeapp.Utils
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val email = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage
    private val _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen


    fun signIn(){
        if (Utils.checkValidation(email.value,pass.value)) {
            auth.signInWithEmailAndPassword(email.value.toString(), pass.value.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("***********LOG IN", "*******SUCCEED")
                        val action =
                            SignInFragmentDirections.actionSignInFragmentToMainFragment()
                        _navigateScreen.value = action

                    } else {
                        statusMessage.value = "Error Message: " + task.exception!!.message.toString()
                    }
                }
        }
    }

    fun goToSignUp(){
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        _navigateScreen.value = action
    }

}