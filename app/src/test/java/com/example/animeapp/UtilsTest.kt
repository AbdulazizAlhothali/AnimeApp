package com.example.animeapp

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class UtilsTest {

    lateinit var utils : Utils
    @Before
    fun setUp() {
        utils = Utils
    }

    @Test
    fun checkValidation() {
        val test1 = utils.checkValidation("asasa@gmail.com","12312312")
        assertTrue(test1)
    }
}