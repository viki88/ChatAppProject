package com.vikination.chatapponeononesample.presentation.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.vikination.chatapponeononesample.data.model.Response
import com.vikination.chatapponeononesample.data.model.User
import com.vikination.chatapponeononesample.domain.auth.AuthUseCase
import com.vikination.chatapponeononesample.utils.Constants

class AuthViewModel @ViewModelInject constructor(private val authUseCase: AuthUseCase, private val firebaseDatabase: FirebaseDatabase) : ViewModel(){

    val authResponse : MutableLiveData<Response<FirebaseUser?>> = MutableLiveData()

    fun signin(email :String, password :String){
        authResponse.postValue(Response.loading(null))
        authUseCase.signin(email, password){
            if (it.isSuccessful){
                authResponse.postValue(Response.success(Firebase.auth.currentUser, message = "Sign in Success, Welcome"))
            }else{
                authResponse.postValue(Response.error(it.exception?.message?:"Authentication is Failed"))
            }
        }
    }

    fun signup(email :String, password :String){
        authResponse.postValue(Response.loading(null))
        authUseCase.signup(email, password){
            if (it.isSuccessful){
                val authUser = Firebase.auth.currentUser
                authResponse.postValue(Response.success(authUser, message = "Sign up for email $email Success, Welcome"))
                authUser?.let { user -> firebaseDatabase.getReference(Constants.ALL_USERS_REFERENCE).child(user.uid).setValue(User(email, user.uid)) }
            }else{
                authResponse.postValue(Response.error(it.exception?.message?:"Authentication is Failed"))
            }
        }
    }

    fun logout() = authUseCase.logout()
}