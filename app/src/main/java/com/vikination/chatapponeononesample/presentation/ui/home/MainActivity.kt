package com.vikination.chatapponeononesample.presentation.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.vikination.chatapponeononesample.R
import com.vikination.chatapponeononesample.databinding.ActivityMainBinding
import com.vikination.chatapponeononesample.presentation.ui.auth.AuthActivity
import com.vikination.chatapponeononesample.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbarHome)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout -> {
                logoutAlert()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutAlert(){
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are sure want to Logout?")
            .setPositiveButton(android.R.string.ok){ dialog,_ ->
                dialog.dismiss()
                viewModel.logout()
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()

            }.setNegativeButton(android.R.string.cancel){ dialog, _ -> dialog.dismiss()}
            .create().show()
    }
}