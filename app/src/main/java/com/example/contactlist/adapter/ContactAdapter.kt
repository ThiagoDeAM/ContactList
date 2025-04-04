package com.example.contactlist.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contactlist.R
import com.example.contactlist.databinding.TileContactBinding
import com.example.contactlist.model.Contact

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>):
    ArrayAdapter<Contact>(
        context,
        R.layout.tile_contact,
        contactList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Recuperar o contato que será usado para preencher os dados da célula
        val contact = contactList[position]

        // Verificar se existe uma célula reciclada ou se é necessário inflar uma nova
        var contactTileView = convertView
        if (contactTileView == null){
            //Infla uma nova célula
            contactTileView = TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
                ).root

            val tileContactViewHolder = TileContactViewHolder(
                contactTileView.findViewById(R.id.name_tv),
                contactTileView.findViewById(R.id.email_tv)
            )

            contactTileView.tag = tileContactViewHolder
        }

        // Preencher a célula com os dados do contato
        val viewHolder = contactTileView.tag as TileContactViewHolder
        viewHolder.nameTv.text = contact.name
        viewHolder.emailTv.text = contact.email

        // Devolver a célula preenchida para o ListView
        return contactTileView
    }

    /*

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Recuperar o contato que será usado para preencher os dados da célula
        val contact = contactList[position]

        // Verificar se existe uma célula reciclada ou se é necessário inflar uma nova
        var contactTileView = convertView
        if (contactTileView == null){
            //Infla uma nova célula
           TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).apply {
               val tileContactViewHolder = TileContactViewHolder(nameTv, emailTv)
               contactTileView = root
               contactTileView.tag = tileContactViewHolder
            }
        }

        // Preencher a célula com os dados do contato
        val viewHolder = contactTileView?.tag as TileContactViewHolder
        viewHolder.nameTv.text = contact.name
        viewHolder.emailTv.text = contact.email

        // Devolver a célula preenchida para o ListView
        return contactTileView
    }

     */


    private data class TileContactViewHolder(val nameTv: TextView, val emailTv: TextView)
}