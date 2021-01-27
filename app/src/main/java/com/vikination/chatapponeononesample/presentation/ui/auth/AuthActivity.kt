package com.vikination.chatapponeononesample.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vikination.chatapponeononesample.data.model.Response
import com.vikination.chatapponeononesample.databinding.ActivityAuthBinding
import com.vikination.chatapponeononesample.presentation.ui.home.MainActivity
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
            if (isValidInput()){
                viewModel.signup(
                    binding.emailInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            }
        }

        binding.buttonSignin.setOnClickListener {
            if (isValidInput()){
                viewModel.signin(
                    binding.emailInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            }
        }
        
        viewModel.authResponse.observe(this){
            Log.i("ChatApp", "onCreate: ${it.status}")
            when(it.status){
                Response.Status.SUCCESS -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    goToHome()
                }
                Response.Status.LOADING -> {

                }
                Response.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToHome(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun isValidInput() : Boolean =
        when{
            binding.emailInput.text.isEmpty() -> {
                binding.emailInput.error = "Email can't blank"
                false
            }

            !isEmailValid(binding.emailInput.text.toString()) -> {
                binding.emailInput.error = "Please fill with valid email address"
                false
            }

            binding.passwordInput.text.isEmpty() -> {
                binding.passwordInput.error = "Password can't blank"
                false
            }

            else -> true
        }

    private fun isEmailValid(email :String) :Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

}