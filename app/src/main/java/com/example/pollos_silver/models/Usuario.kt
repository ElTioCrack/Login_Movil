package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Usuario (
    @SerializedName("ci")
    var ci: Int,

    @SerializedName("contrasena")
    var contrasena: String,

    @SerializedName("tipousuario")
    var tipousuario: Tipousuario,
)