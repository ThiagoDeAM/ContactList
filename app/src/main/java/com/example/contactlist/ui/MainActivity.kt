package com.example.contactlist.ui

//noinspection SuspiciousImport
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlist.model.Constant.EXTRA_CONTACT
import com.example.contactlist.model.Contact
import com.example.contactlist.R
import com.example.contactlist.adapter.ContactAdapter
import com.example.contactlist.adapter.ContactRvAdapter
import com.example.contactlist.controller.MainController
import com.example.contactlist.databinding.ActivityMainBinding
import com.example.contactlist.model.Constant.EXTRA_CONTACT_ARRAY
import com.example.contactlist.model.Constant.EXTRA_VIEW_CONTACT

class MainActivity : AppCompatActivity(), OnContactClickListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    private val contactAdapter: ContactRvAdapter by lazy {
        ContactRvAdapter(contactList, this)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    // Controller
    // --> Database Inspector
    private val mainController: MainController by lazy {
        MainController(this)
    }

    // Handler
    companion object {
        const val GET_CONTACTS_MESSAGE = 1
        const val GET_CONTACTS_INTERVAL = 2000L
    }
    val getContactsHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == GET_CONTACTS_MESSAGE) {
                mainController.getContacts()
                sendMessageDelayed(
                    obtainMessage().apply { what = GET_CONTACTS_MESSAGE },
                    GET_CONTACTS_INTERVAL
                )
            }
            else {
                val contactArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    msg.data.getParcelableArray(EXTRA_CONTACT_ARRAY, Contact::class.java)
                }
                else{
                    msg.data.getParcelableArray(EXTRA_CONTACT_ARRAY)
                }
                contactList.clear()
                contactArray?.forEach { contactList.add(it as Contact) }
                contactAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_list)

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if(result.resultCode == RESULT_OK){
                val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    result.data?.getParcelableExtra(EXTRA_CONTACT, Contact::class.java)
                }
                else{
                    result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                }
                contact?.let{ receveidContact ->
                    // Verificar se é um novo conato ou se é um contato editado
                    val position = contactList.indexOfFirst { it.id ==  receveidContact.id }
                    if (position == -1){
                        contactList.add(receveidContact)
                        contactAdapter.notifyItemInserted(contactList.lastIndex)
                        mainController.insertContact(receveidContact)
                    }
                    else{
                        contactList[position] = receveidContact
                        contactAdapter.notifyItemChanged(position)
                        mainController.modifyContact(receveidContact)
                    }
                }
            }
        }

        amb.contactRv.adapter = contactAdapter
        amb.contactRv.layoutManager = LinearLayoutManager(this)

        getContactsHandler.sendMessageDelayed(
            Message().apply { what = GET_CONTACTS_MESSAGE },
            GET_CONTACTS_INTERVAL
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_contact_mi -> {
                carl.launch(Intent(this, ContactActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onContactClick(position: Int) {
        Intent(this, ContactActivity::class.java).apply {
            putExtra(EXTRA_CONTACT, contactList[position])
            putExtra(EXTRA_VIEW_CONTACT, true)
            startActivity(this)
        }
    }

    override fun onRemoveContactMenuItemClick(position: Int) {
        mainController.removeContact(contactList[position])
        contactList.removeAt(position)
        contactAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Contact removed!", Toast.LENGTH_SHORT).show()
    }

    override fun onEditContactMenuItemClick(position: Int) {
        Intent(this, ContactActivity::class.java).apply {
            putExtra(EXTRA_CONTACT, contactList[position])
            carl.launch(this)
        }
    }
}