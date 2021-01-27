package com.vikination.chatapponeononesample.presentation.ui.auth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vikination.chatapponeononesample.data.model.Response
import com.vikination.chatapponeononesample.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding :ActivityAuthBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.buttonSignup.setOnClickListener { 
            viewModel.signup(
                binding.emailInput.text.toString(),
                binding.passwordInput.text.toString()
            ) 
        }

        binding.buttonSignin.setOnClickListener {
            viewModel.signin(
                binding.emailInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }
        
        viewModel.authResponse.observe(this){
            Log.i("ChatApp", "onCreate: ${it.status}")
            when(it.status){
                Response.Status.SUCCESS -> {
                    Toast.makeText(this, "sign in success : ${it.data?.uid}", Toast.LENGTH_SHORT).show()
                }
                Response.Status.LOADING -> {

                }
                Response.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}