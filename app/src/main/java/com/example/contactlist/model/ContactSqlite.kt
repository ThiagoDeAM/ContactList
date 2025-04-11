package com.example.contactlist.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.contactlist.R
import java.sql.SQLException

class ContactSqlite(context: Context): ContactDao {
    companion object {
        private val CONTACT_DATABASE_FILE = "contactList"
        private val CONTACT_TABLE = "contact"
        private val ID_COLUMN = "id"
        private val NAME_COLUMN = "name"
        private val ADDRESS_COLUMN = "address"
        private val PHONE_COLUMN = "phone"
        private val EMAIL_COLUMN = "email"

        val CREATE_CONTACT_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS $CONTACT_TABLE (" +
                "$ID_COLUMN INTEGER NOT NULL PRIMARY KEY," +
                "$NAME_COLUMN TEXT NOT NULL," +
                "$ADDRESS_COLUMN TEXT NOT NULL," +
                "$PHONE_COLUMN TEXT NOT NULL," +
                "$EMAIL_COLUMN TEXT NOT NULL);"
    }

    // Criando uma instância de SQLite
    private val contactDatabase: SQLiteDatabase = context.openOrCreateDatabase(
        CONTACT_DATABASE_FILE,
        MODE_PRIVATE,
        null
    )

    // Criando a tabela de contatos
    init {
        try {
            contactDatabase.execSQL(CREATE_CONTACT_TABLE_STATEMENT)
        }
        catch (se: SQLException){
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }
    }

    override fun createContact(contact: Contact) =
         contactDatabase.insert(CONTACT_TABLE, null, contact.toContentValues())


    override fun retrieveContact(id: Int): Contact {
        TODO("Not yet implemented")
    }

    override fun retrieveContacts(): MutableList<Contact> {
        TODO("Not yet implemented")
    }

    override fun updateContact(contact: Contact): Int {
        TODO("Not yet implemented")
    }

    override fun deleteContact(id: Int): Int {
        TODO("Not yet implemented")
    }

    private fun Contact.toContentValues(): ContentValues {
        return ContentValues().apply {
            put(ID_COLUMN, id)
            put(NAME_COLUMN, name)
            put(ADDRESS_COLUMN, address)
            put(PHONE_COLUMN, phone)
            put(EMAIL_COLUMN, email)
        }
    }
}