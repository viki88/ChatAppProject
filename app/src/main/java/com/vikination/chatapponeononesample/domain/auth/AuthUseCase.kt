package com.vikination.chatapponeononesample.domain.auth

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.vikination.chatapponeononesample.data.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val authRepository: AuthRepository) {

    fun signin(email :String, password :String, onCompleteListener: OnCompleteListener<AuthResult>) =
        authRepository.signin(email, password, onCompleteListener)
    fun signup(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>) =
        authRepository.signup(email, password, onCompleteListener)
    fun logout() = authRepository.logout()
}