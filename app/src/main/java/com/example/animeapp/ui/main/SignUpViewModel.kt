package com.example.animeapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.animeapp.data.User
import com.example.animeapp.event.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private var firebaseUserId: String = ""
    lateinit var ref: DatabaseReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    val email = MutableLiveData<String>()
    val pass = MutableLiveData<String>()
    val confirm = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val signup_username = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = statusMessage
    private val _navigateScreen = MutableLiveData<Event<NavDirections>>()
    val navigateScreen: LiveData<Event<NavDirections>> = _navigateScreen


    fun authentication(){
        if ( checkEmpty(arrayListOf(name.value, signup_username.value, email.value, pass.value))) {
            if (confirm.value != pass.value) {
                statusMessage.value = Event("Password mismatch")

            } else {
                auth.createUserWithEmailAndPassword(email.value.toString(), pass.value.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "................TASK SUCCEED.................")
                            firebaseUserId = auth.currentUser!!.uid
                            ref = FirebaseDatabase.getInstance().reference.child("Users")
                                .child(firebaseUserId)
                            val user = User(
                                firebaseUserId,
                                name.value.toString(),
                                signup_username.value.toString(),
                                email.value.toString())
                            firebaseFirestore = FirebaseFirestore.getInstance()
                            firebaseFirestore.collection("users").document(firebaseUserId)
                                .set(user)
                                .addOnSuccessListener {
                                    Log.d("TAG", "DocumentSnapshot successfully written!")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TAG", "Error writing document", e)
                                }

                            val action =
                                SignUpFragmentDirections.actionSignUpFragmentToAnimeFragment()
                            _navigateScreen.value = Event(action)
                        } else {

                            statusMessage.value = Event("Error Message: " + task.exception!!.message.toString())
                        }
                    }

            }
        }
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