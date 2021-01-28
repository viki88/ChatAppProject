package com.vikination.chatapponeononesample.presentation.ui.contactlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikination.chatapponeononesample.data.model.Response
import com.vikination.chatapponeononesample.databinding.ActivityContactListBinding
import com.vikination.chatapponeononesample.databinding.LayoutAddContactBinding
import com.vikination.chatapponeononesample.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListActivity : AppCompatActivity() {
    private lateinit var binding :ActivityContactListBinding
    private val viewModel :ContactListViewModel by viewModels()
    private lateinit var adapter: ContactListAdapter
    lateinit var addListDialog: AlertDialog
    lateinit var layoutAddContactBinding: LayoutAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setupList()
        createDialogAddContact()

        binding.addNewContact.setOnClickListener { addListDialog.show() }

        viewModel.contactListResponse.observe(this){
            Log.i("TAG", "observe: ${it.status}")
            when(it.status){
                Response.Status.SUCCESS -> {
                    it.data?.let { dataContactList ->  adapter.updateContactList(dataContactList)}
                }
                Response.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Response.Status.LOADING -> {

                }
            }
        }

        viewModel.searchUserResponse.observe(this){
            Log.i("TAG", "observe: ${it.status}")
            when(it.status){
                Response.Status.SUCCESS -> {
                    Log.i("TAG", "search user: ${it.data}")
                    if (it.data != null){
                        viewModel.addNewContact(it.data)
                        addListDialog.dismiss()
                    }else Toast.makeText(this, "User is not exist", Toast.LENGTH_SHORT).show()
                }
                Response.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Response.Status.LOADING -> {

                }
            }
        }

        viewModel.getContactList()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbarContactlist)
        supportActionBar?.title = "Contact List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupList(){
        adapter = ContactListAdapter()
        binding.contactlistView.adapter = adapter
        binding.contactlistView.layoutManager = LinearLayoutManager(this)
        binding.contactlistView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createDialogAddContact(){
        layoutAddContactBinding = LayoutAddContactBinding.inflate(LayoutInflater.from(this))
        addListDialog = AlertDialog.Builder(this)
            .setTitle("Add New Contact")
            .setView(layoutAddContactBinding.root)
            .create()
        layoutAddContactBinding.cancelAddButton.setOnClickListener { addListDialog.dismiss() }
        layoutAddContactBinding.addContactButton.setOnClickListener {
            if (isValidEmail()) viewModel.searchUserByEmail(layoutAddContactBinding.newEmail.text.toString())
        }
    }

    private fun isValidEmail() :Boolean{
        return when{
            layoutAddContactBinding.newEmail.text.isEmpty() -> {
                layoutAddContactBinding.newEmail.error = "email can't blank"
                false
            }

            !Utils.isEmailValid(layoutAddContactBinding.newEmail.text.toString()) -> {
                layoutAddContactBinding.newEmail.error = "email address not valid"
                false
            }

            else -> true
        }
    }
}