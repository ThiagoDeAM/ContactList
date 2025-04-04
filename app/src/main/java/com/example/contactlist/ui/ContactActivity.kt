package com.example.contactlist.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contactlist.model.Constant.EXTRA_CONTACT
import com.example.contactlist.model.Contact
import com.example.contactlist.R
import com.example.contactlist.databinding.ActivityContactBinding
import com.example.contactlist.model.Constant.EXTRA_VIEW_CONTACT

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_list)

        val receveidContact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(EXTRA_CONTACT, Contact::class.java)
        }
        else{
            intent.getParcelableExtra<Contact>(EXTRA_CONTACT)
        }
        receveidContact?.let{
            with(acb){
                nameEt.setText(it.name)
                addressEt.setText(it.address)
                phoneEt.setText(it.phone)
                emailEt.setText(it.email)

                // Verificando se é só visualização de contato
                val viewContact = intent.getBooleanExtra(EXTRA_VIEW_CONTACT, false)
                if(viewContact){
                    supportActionBar?.subtitle = "View contact"
                    nameEt.isEnabled = false
                    addressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }

        }

        with(acb){
            saveBt.setOnClickListener {
                Contact(
                    id = receveidContact?.id?:hashCode(),
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