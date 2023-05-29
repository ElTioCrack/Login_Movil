package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Tipousuario(
    @SerializedName("id_tipousuario")
    var id_tipousuario: Int,

    @SerializedName("tipo_tipousuario")
    var tipo: String,
)
