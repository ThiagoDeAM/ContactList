package com.example.contactlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactlist.Constant.EXTRA_CONTACT
import com.example.contactlist.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_list)

        with(acb){
            saveBt.setOnClickListener {
                Contact(
                    id = hashCode(),
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                    ).let { contact ->
                        Intent().apply {
                            putExtra(EXTRA_CONTACT, contact)
                            setResult(RESULT_OK, this)
                        }
                    }
                    finish()
            }
        }


    }
}