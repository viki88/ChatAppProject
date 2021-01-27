package com.vikination.chatapponeononesample.presentation.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vikination.chatapponeononesample.R
import com.vikination.chatapponeononesample.databinding.ActivitySplashScreenBinding
import com.vikination.chatapponeononesample.presentation.ui.auth.AuthActivity
import com.vikination.chatapponeononesample.presentation.ui.home.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding :ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserLogin()
        },2000L)
    }

    private fun checkUserLogin() {
        if (Firebase.auth.currentUser != null)startActivity(Intent(this, MainActivity::class.java))
        else startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}