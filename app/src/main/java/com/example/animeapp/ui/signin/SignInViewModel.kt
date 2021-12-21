package com.example.animeapp.ui.signin

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.animeapp.event.Event
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    //private var firebaseUserId: String = ""
    val email = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = statusMessage
    private val _navigateScreen = MutableLiveData<Event<NavDirections>>()
    val navigateScreen: LiveData<Event<NavDirections>> = _navigateScreen


    fun signIn(){
        if (checkEmpty(arrayListOf(email.value, pass.value))) {
            auth.signInWithEmailAndPassword(email.value.toString(), pass.value.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("***********LOG IN", "*******SUCCEED")
                        val action =
                            SignInFragmentDirections.actionSignInFragmentToAnimeFragment()
                        _navigateScreen.value = Event(action)

                    } else {
                        statusMessage.value = Event("Error Message: " + task.exception!!.message.toString())
                    }
                }
        }
    }

    fun goToSignUp(){
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        _navigateScreen.value = Event(action)
    }




    private fun checkEmpty(arrOfEditText: ArrayList<String?>): Boolean {
        var returnValue = false
        for (i in arrOfEditText) {
            if (i == "") {
                statusMessage.value = Event("fields must be filled")
                returnValue = false
                break
            } else {
                returnValue = true
            }
        }
        return returnValue
    }
}