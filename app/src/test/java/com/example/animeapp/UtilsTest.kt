package com.example.animeapp


import org.junit.Assert.*
import org.junit.Test
import java.util.regex.Pattern

class UtilsTest {


    val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun checkValidation (email:String?,pass:String?):Boolean {
        val isEmailValid: Boolean
        val isPasswordValid: Boolean
        when {
            email.isNullOrEmpty() -> {

                isEmailValid = false
            }
            !emailRegex.matcher(email).matches() -> {
                isEmailValid = false
            }
            else -> {
                isEmailValid = true
            }
        }
        when {
            pass.isNullOrEmpty() -> {
                isPasswordValid = false
            }
            pass.length < 8 -> {
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
            }
        }
        return isEmailValid && isPasswordValid
    }

    @Test
    fun checkValidation() {
        val test1 = checkValidation("asasa@gmail.com","12312312")
        val test2 = checkValidation("asasagmail.com","12312312")
        val test3 = checkValidation("asasa@gmail.com","123123")
        assertTrue(test1)
        assertFalse(test2)
        assertFalse(test3)

    }
}