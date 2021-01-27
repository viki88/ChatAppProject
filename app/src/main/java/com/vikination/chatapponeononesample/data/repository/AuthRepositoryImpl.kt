package com.vikination.chatapponeononesample.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepositoryImpl :AuthRepository{

    override fun signin(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>){
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener)
    }

    override fun signup(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>){
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener (onCompleteListener)
    }

    override fun logout() {
        Firebase.auth.signOut()
    }
}