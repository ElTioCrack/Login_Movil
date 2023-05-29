package com.example.pollos_silver.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    // constants
    val SHARED_NAME = "session_start"
    val SHARED_CI = "ci"
    val SHARED_CONTRASENA = "contrasena"
    val SHARED_TIPO_USUARIO = "tipo_usuario"

    //init
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    // save
    fun save_ci(value: Int) = sharedPreferences.edit().putInt(SHARED_CI, value).apply()

    fun save_contrasena(value: String) = sharedPreferences.edit().putString(SHARED_CONTRASENA, value).apply()

    fun save_tipo_usuario(value: String) = sharedPreferences.edit().putString(SHARED_TIPO_USUARIO, value).apply()

    // get
    fun get_ci(): Int = sharedPreferences.getInt(SHARED_CI, 0)

    fun get_contrasena(): String = sharedPreferences.getString(SHARED_CONTRASENA, "")!!

    fun get_tipo_usuario(): String = sharedPreferences.getString(SHARED_TIPO_USUARIO, "")!!


    // delete
    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}