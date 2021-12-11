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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.animeapp.R
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var username_email: EditText
    private lateinit var pass: EditText
    private lateinit var login: Button
    private lateinit var go_toRegisterPage: TextView
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_in_fragment, container, false)


    }
    private fun retrieveData(){

        val myPref = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val name = myPref.getString("username", " ")
        username_email.setText(name)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// initialization

        username_email = view.findViewById(R.id.signin_etUsername)
        pass = view.findViewById(R.id.signin_etPassword)
        login = view.findViewById(R.id.signin_btnSignIn)
        go_toRegisterPage = view.findViewById(R.id.signin_tvSignUp)

        // FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()

        //showing username name or email
        retrieveData()
        // session management
        /*val currentuser = auth.currentUser
        if (currentuser != null) {
            val action = SignInFragmentDirections.actionSignInFragmentToAnimeFragment()
            findNavController().navigate(action)
        }*/

        login.setOnClickListener {

            if (checkEmpty(arrayListOf(username_email, pass))) {
                val user_pass = pass.text.toString()
                val user_email_username = username_email.text.toString()
                auth.signInWithEmailAndPassword(user_email_username, user_pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.e("***********LOG IN", "*******SUCCEED")
                            val action =
                                SignInFragmentDirections.actionSignInFragmentToAnimeFragment()
                            it.findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error Message: " + task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }


            }

            val myPref = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val editor = myPref.edit()

            editor.putString("username", username_email.text.toString())

            editor.apply()


        }

        go_toRegisterPage.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun checkEmpty(arrOfEditText: ArrayList<EditText>): Boolean {
        var returnValue = false
        for (i in arrOfEditText) {
            if (i.text.toString() == "") {
                i.error = "must filled"
                returnValue = false
                break
            } else {
                returnValue = true
            }
        }
        return returnValue
    }

}