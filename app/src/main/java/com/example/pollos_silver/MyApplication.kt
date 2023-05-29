package com.example.pollos_silver

import android.app.Application
import com.example.pollos_silver.utils.SharedPreferencesManager
import com.example.register.Models.Cliente
import com.example.register.Models.Empleado
import com.example.register.Models.UserLoginInfo

class MyApplication : Application() {
    companion object {
        lateinit var SharedPreferences: SharedPreferencesManager
        var Usuario: Any? = null
    }

    override fun onCreate() {
        super.onCreate()
        SharedPreferences = SharedPreferencesManager(applicationContext)
    }

    fun getRolAsString(): String {
        return when (Usuario) {
            is Empleado -> (Usuario as Empleado).usuario.tipousuario.tipo
            is Cliente -> (Usuario as Cliente).usuario.tipousuario.tipo
            else -> "a"
        }
    }
}