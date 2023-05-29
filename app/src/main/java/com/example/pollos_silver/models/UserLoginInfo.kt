package com.example.register.Models

object UserLoginInfo {
    var usuario: Any? = null
    var rol: String = ""

    fun getRolAsString(): String {
        return when (usuario) {
            is Empleado -> (usuario as Empleado).usuario.tipousuario.tipo
            is Cliente -> (usuario as Cliente).usuario.tipousuario.tipo
            else -> "a"
        }
    }

    fun getDataAsString(): String {
        return usuario?.toString() ?: ""
    }

    val data: Any?
        get() = usuario
}
