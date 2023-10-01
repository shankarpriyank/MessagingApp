package com.priyank.messagingappbranch.data.local

import android.content.SharedPreferences

class Header(private val sharedPreferences: SharedPreferences) {
    fun updateHeader(header: String){
        sharedPreferences.edit().putString("headers", header)
            .apply()
    }

    fun getHeader():String{
        val header = sharedPreferences.getString("headers", "0")
        return header ?:"0"
    }
}