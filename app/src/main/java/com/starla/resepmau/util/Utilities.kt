package com.starla.resepmau.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Utilities {
    companion object {
        fun getToken(c : Context) : String? {
            val s = c.getSharedPreferences("TOKEN", MODE_PRIVATE)
            val token = s?.getString("TOKEN", "UNDEFINED")
            return token
        }
    }
}