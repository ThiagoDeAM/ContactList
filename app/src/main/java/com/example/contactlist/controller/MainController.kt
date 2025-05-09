package com.example.contactlist.controller

import com.example.contactlist.model.Contact
import com.example.contactlist.model.ContactDao
import com.example.contactlist.model.ContactSqlite
import com.example.contactlist.ui.MainActivity
import java.lang.Thread

class MainController(mainActivity: MainActivity) {
 //   private val contactDao: ContactDao = ContactSqlite(mainActivity)

    private val contactDao: ContactDao = Room.databaseBuilder(
        mainActivity,
        ContactRoomDb::class.java,
        "contact-database"
    ).build().contactDao()

    fun insertContact(contact: Contact) {
        //substituido thread {}.start() por MainScope (corroutine)
        MainScope().launch {
            contactDao.createContact(contact)
        }
    } //E se fosse pra retornar um Long num toast? "O id retornado foi: ..." Como ficaria nesse caso das threads?
    // EXERCÍCIO: A parte de modificação, remoção, fazer usando corrotinas...

    fun removeContact(contact: Contact) = contactDao.deleteContact(contact)
}