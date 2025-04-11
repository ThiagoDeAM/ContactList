package com.example.contactlist.ui

sealed interface OnContactClickListener {
    fun onContactClick(position: Int)
}