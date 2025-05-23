package com.example.contactlist.controller

import android.os.Message
import android.widget.Toast
import androidx.room.Room
import com.example.contactlist.model.Constant
import com.example.contactlist.model.Constant.EXTRA_CONTACT_ARRAY
import com.example.contactlist.model.Contact
import com.example.contactlist.model.ContactDao
import com.example.contactlist.model.ContactFirebaseDatabase
import com.example.contactlist.model.ContactRoomDb
import com.example.contactlist.model.ContactSqlite
import com.example.contactlist.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainController(private val mainActivity: MainActivity) {
    //private val contactDao: ContactDao = ContactSqlite(mainActivity)

    /*
    private val contactDao: ContactDao = Room.databaseBuilder(
        mainActivity,
        ContactRoomDb::class.java,
        "contact-database"
    ).build().contactDao()*/

    private val contactDao: ContactDao = ContactFirebaseDatabase()
    private val databaseCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun insertContact(contact: Contact) {
        //substituido thread {}.start() por MainScope (coroutine)
        databaseCoroutineScope.launch {
            contactDao.createContact(contact)
        }
    }
    //E se fosse pra retornar um Long num toast? "O id retornado foi: ..." Como ficaria nesse caso das threads?

    fun getContact(id: Int) = databaseCoroutineScope.launch {
        contactDao.retrieveContact(id)
    }

    fun getContacts() {
        databaseCoroutineScope.launch {
            val contactList = contactDao.retrieveContacts()
            mainActivity.getContactsHandler.sendMessage(Message().apply {
                data.putParcelableArray(EXTRA_CONTACT_ARRAY, contactList.toTypedArray())
            })
        }
    }

    fun modifyContact(contact: Contact){
        databaseCoroutineScope.launch {
            contactDao.updateContact(contact)
        }
    }

    fun removeContact(contact: Contact) {
        databaseCoroutineScope.launch {
            contactDao.deleteContact(contact)
        }
    }
}