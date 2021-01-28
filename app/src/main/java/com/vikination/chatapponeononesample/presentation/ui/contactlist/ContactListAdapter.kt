package com.vikination.chatapponeononesample.presentation.ui.contactlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikination.chatapponeononesample.data.model.User
import com.vikination.chatapponeononesample.databinding.RowContactListBinding

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>(){

    private var contactList = listOf<User>()

    inner class ContactViewHolder(private val binding: RowContactListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.emailContact.text = user.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = RowContactListBinding.inflate(LayoutInflater.from(parent.context))
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int = contactList.size

    fun updateContactList(contactList :List<User>){
        this.contactList = contactList
        notifyDataSetChanged()
    }
}