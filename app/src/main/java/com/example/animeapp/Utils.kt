package com.example.animeapp

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animeapp.event.Event


object Utils {
    const val CHECK = "check"
    const val LANGUAGE = "lang"
    const val SETTINGS = "settings"
    const val PASSWORD_MISMATCH = "Password mismatch"
    const val FIELDS_MUST_BE_FILLED = "fields must be filled"
    const val PASSWORD_COUNT = "Password must be more than 8 numbers"
    const val WRONG_EMAIL = "Email is wrong"
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = statusMessage

    fun checkValidation (email:String?,pass:String?):Boolean {
        val isEmailValid: Boolean
        val isPasswordValid: Boolean
        when {
            email.isNullOrEmpty() -> {
                statusMessage.value = Event(FIELDS_MUST_BE_FILLED)
                isEmailValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                statusMessage.value = Event(WRONG_EMAIL)
                isEmailValid = false
            }
            else -> {
                isEmailValid = true
            }
        }
        when {
            pass.isNullOrEmpty() -> {
                statusMessage.value = Event(FIELDS_MUST_BE_FILLED)
                isPasswordValid = false
            }
            pass.length < 8 -> {
                statusMessage.value = Event(PASSWORD_COUNT)
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
            }
        }
        return isEmailValid && isPasswordValid
    }
}