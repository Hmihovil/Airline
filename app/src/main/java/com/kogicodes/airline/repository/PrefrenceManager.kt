package com.kogicodes.airline.repository

import android.content.Context
import android.content.SharedPreferences
import com.kogicodes.airline.models.oauth.Token

class PrefrenceManager(internal var _context: Context) {


    internal var pref: SharedPreferences


    internal var editor: SharedPreferences.Editor


    internal var PRIVATE_MODE = 0

    val token: Token
        get() {
            val token = Token()
            token.expiryDate = pref.getLong(KEY_TOKEN_EXPIRY_DATE, 0)
            token.accessToken = pref.getString(KEY_TOKEN, null)
            return token
        }


    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun saveToken(token: Token) {
        editor.putString(KEY_TOKEN, token.accessToken)
        editor.putLong(KEY_TOKEN_EXPIRY_DATE, token.expiryDate)
        editor.apply()
    }

    companion object {
        private val PREF_NAME = "airline"
        private val KEY_TOKEN = "bearerToken"
        private val KEY_TOKEN_EXPIRY_DATE = "bearerTokenExpiryDate"
    }


}
