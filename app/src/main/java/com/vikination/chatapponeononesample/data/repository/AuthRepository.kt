package com.vikination.chatapponeononesample.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    fun signin(email :String, password :String, onCompleteListener: OnCompleteListener<AuthResult>)
    fun signup(email :String, password :String, onCompleteListener: OnCompleteListener<AuthResult>)
    fun logout()
}