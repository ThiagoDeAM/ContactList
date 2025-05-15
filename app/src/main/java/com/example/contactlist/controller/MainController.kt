package com.example.contactlist.controller

import android.widget.Toast
import androidx.room.Room
import com.example.contactlist.model.Contact
import com.example.contactlist.model.ContactDao
import com.example.contactlist.model.ContactRoomDb
import com.example.contactlist.model.ContactSqlite
import com.example.contactlist.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainController(mainActivity: MainActivity) {
    //private val contactDao: ContactDao = ContactSqlite(mainActivity)

    private val contactDao: ContactDao = Room.databaseBuilder(
        mainActivity,
        ContactRoomDb::class.java,
        "contact-database"
    ).build().contactDao()

    fun insertContact(contact: Contact) {
        //substituido thread {}.start() por MainScope (corroutine)
        MainScope().launch {
            withContext(Dispatchers.IO) {
                contactDao.createContact(contact)
            }
        }
    }
    //E se fosse pra retornar um Long num toast? "O id retornado foi: ..." Como ficaria nesse caso das threads?
    // EXERCÍCIO: A parte de modificação, remoção, fazer usando corrotinas...

    fun getContact(id: Int) = contactDao.retrieveContact(id)
    fun getContacts() = contactDao.retrieveContacts()
    fun modifyContact(contact: Contact){
        MainScope().launch {
            withContext(Dispatchers.IO) {
                contactDao.updateContact(contact)
            }
        }
    }
    fun removeContact(contact: Contact) {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                contactDao.deleteContact(contact)
            }
        }
    }
}