package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Cliente(
    @SerializedName("usuario")
    var usuario: Usuario,

    @SerializedName("nombres")
    var nombres: String,

    @SerializedName("apellidos")
    var apellidos: String,

    @SerializedName("telefono")
    var telefono: String,

    @SerializedName("descripcion_direccion")
    var descripcion_direccion: String,

    @SerializedName("fecha_hora")
    var fecha_hora: String
)
