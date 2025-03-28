package com.example.contactlist

//noinspection SuspiciousImport
import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this, R.layout.simple_list_item_1,
            contactList.map {
                "${it.name} - ${it.email}"
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillContactList()

        amb.contactLv.adapter = contactAdapter
    }

    private fun fillContactList(){
        for (i in 1 .. 50){
            contactList.add(
                Contact(
                    1,
                    "nome $i",
                    "endereco $i",
                    "phone $i",
                    "email $i"
                )
            )
        }
    }
}