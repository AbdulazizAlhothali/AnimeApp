package com.example.animeapp.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.animeapp.Utils
import com.example.animeapp.data.firestore.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var firebaseUserId: String = ""
    val email = MutableLiveData<String>()
    val pass = MutableLiveData<String>()
    val confirm = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage
    private val _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen


    fun authentication(){
        if (Utils.checkValidation(email.value,pass.value)) {
            if (confirm.value != pass.value) {
                statusMessage.value = Utils.PASSWORD_MISMATCH

            } else {
                auth.createUserWithEmailAndPassword(email.value.toString(), pass.value.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "................TASK SUCCEED.................")
                            firebaseUserId = auth.currentUser!!.uid
                            FirebaseDatabase.getInstance().reference.child("Users")
                                .child(firebaseUserId)
                            val user = User(
                                firebaseUserId,
                                email.value.toString())
                            val firebaseFirestore = FirebaseFirestore.getInstance()
                            firebaseFirestore.collection("users").document(firebaseUserId)
                                .set(user)
                                .addOnSuccessListener {
                                    Log.d("TAG", "DocumentSnapshot successfully written!")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TAG", "Error writing document", e)
                                }

                            val action = SignUpFragmentDirections.actionSignUpFragmentToMainFragment()
                            _navigateScreen.value = action
                        } else {

                            statusMessage.value = "Error Message: " + task.exception!!.message.toString()
                        }
                    }

            }
        }
    }

    fun goToSignIn(){
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        _navigateScreen.value = action
    }

}