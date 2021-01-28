package com.vikination.chatapponeononesample.presentation.ui.contactlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.vikination.chatapponeononesample.data.model.Response
import com.vikination.chatapponeononesample.data.model.User
import com.vikination.chatapponeononesample.utils.Constants

class ContactListViewModel @ViewModelInject constructor(private val firebaseDatabase: FirebaseDatabase) : ViewModel(){

    val contactListResponse = MutableLiveData<Response<List<User>>>()
    val searchUserResponse = MutableLiveData<Response<User?>>()

    fun getContactList(){
        val uid = Firebase.auth.uid
        uid?.let {
            firebaseDatabase.getReference(it).child(Constants.CONTACT_LIST_REFERENCE).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userList = arrayListOf<User>()
                        for (usersSnapshot in snapshot.children) {
                            val user = usersSnapshot.getValue<User>()
                            user?.let { userData ->  userList.add(userData) }
                        }
                        contactListResponse.postValue(Response.success(userList))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        contactListResponse.postValue(Response.error(error.message))
                    }

                })
        }
    }

    fun searchUserByEmail(email :String){
        val uid = Firebase.auth.uid
        uid?.let {
            firebaseDatabase.getReference(Constants.ALL_USERS_REFERENCE).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var searchUser: User? = null
                        for (usersSnapshot in snapshot.children) {
                            val user = usersSnapshot.getValue<User>()
                            user?.let { userData ->
                                if (userData.email == email) {
                                    searchUser = userData
                                }
                            }
                            if (searchUser != null) break
                        }
                        searchUserResponse.postValue(Response.success(searchUser))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        searchUserResponse.postValue(Response.error(error.message))
                    }

                })
        }
    }

    fun addNewContact(user: User){
        val uid = Firebase.auth.uid
        uid?.let {
            firebaseDatabase.getReference(it).child(Constants.CONTACT_LIST_REFERENCE).child(user.uid).setValue(user)
        }
    }

}