package com.example.animeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern.compile


object Utils {
    const val CHECK = "check"
    const val LANGUAGE = "lang"
    const val SETTINGS = "settings"
    const val PASSWORD_MISMATCH = "Password mismatch"
    const val FIELDS_MUST_BE_FILLED = "fields must be filled"
    const val PASSWORD_COUNT = "Password must be more than 8 numbers"
    const val WRONG_EMAIL = "Email is wrong"
    private val emailRegex = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage

    fun checkValidation (email:String?,pass:String?):Boolean {
        val isEmailValid: Boolean
        val isPasswordValid: Boolean
        when {
            email.isNullOrEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isEmailValid = false
            }
            !emailRegex.matcher(email).matches() -> {
                statusMessage.value = WRONG_EMAIL
                isEmailValid = false
            }
            else -> {
                isEmailValid = true
            }
        }
        when {
            pass.isNullOrEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isPasswordValid = false
            }
            pass.length < 8 -> {
                statusMessage.value = PASSWORD_COUNT
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
            }
        }
        return isEmailValid && isPasswordValid
    }
}