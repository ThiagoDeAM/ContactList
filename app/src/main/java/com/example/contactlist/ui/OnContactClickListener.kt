package com.example.contactlist.ui

sealed interface OnContactClickListener {
    fun onContactClick(position: Int)

    // FUnções abstratas relacionadas ao menu de contexto. Poderiam estar numa interface separada e
    // mais específica.
    fun onRemoveContactMenuItemClick(position: Int)
    fun onEditContactMenuItemClick(position: Int)
}