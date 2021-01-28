package com.vikination.chatapponeononesample.utils

object Utils {
    fun isEmailValid(email :String) :Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}