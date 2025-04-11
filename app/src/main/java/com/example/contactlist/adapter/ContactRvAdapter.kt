package com.example.contactlist.adapter

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.R
import com.example.contactlist.databinding.TileContactBinding
import com.example.contactlist.model.Contact
import com.example.contactlist.ui.OnContactClickListener

class ContactRvAdapter(
    private val contactList: MutableList<Contact>,
    private val onContactClickListener: OnContactClickListener
): RecyclerView.Adapter<ContactRvAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(tcb: TileContactBinding): RecyclerView.ViewHolder(tcb.root){
        val nameTv: TextView = tcb.nameTv
        val emailTv: TextView = tcb.emailTv

        init {
            // Criando o menu de contexto para cada célula associada a um novo holder
            tcb.root.setOnCreateContextMenuListener{ menu, v, menuInfo ->
                (onContactClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_main, menu)
                menu.findItem(R.id.edit_contact_mi).setOnMenuItemClickListener {
                    onContactClickListener.onEditContactMenuItemClick(adapterPosition)
                    true
                }
                menu.findItem(R.id.remove_contact_mi).setOnMenuItemClickListener {
                    onContactClickListener.onRemoveContactMenuItemClick(adapterPosition)
                    true
                }
            }
            // Iniciando o listener de clique curto na célula associada a um novo holder
            tcb.root.setOnClickListener{ onContactClickListener.onContactClick(adapterPosition)}
        }
    }

    // Criar um ViewHolder significa inflar uma célula. Para criar a célula:
    // Chamada somente quando um novo holder  (e consequentemente uma nova célula) precisa ser criada.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder = ContactViewHolder(
        TileContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    // Se a célula já existir, essa função será chamada para substituir a célula
    // Chamada sempre que os dados de um holder (ou seja, da célula) precisam ser preenchidos ou trocados
    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        contactList[position].let { contact ->
            with(holder) {
                nameTv.text = contact.name
                emailTv.text = contact.email
            }
        }
    }

    // Retorna o número de elementos do DataSource
    override fun getItemCount(): Int = contactList.size
}