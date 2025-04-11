package com.example.contactlist.controller

import com.example.contactlist.model.Contact
import com.example.contactlist.model.ContactDao
import com.example.contactlist.model.ContactSqlite
import com.example.contactlist.ui.MainActivity

class MainController(mainActivity: MainActivity) {
    private val contactDao: ContactDao = ContactSqlite(mainActivity)

    fun insertContact(contact: Contact) = contactDao.createContact(contact)
}