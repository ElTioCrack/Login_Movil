package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Empleado(
//    @SerializedName("")
    var usuario: Usuario,

//    @SerializedName("")
    var sucursal: Sucursal,

    @SerializedName("nombres")
    var nombres: String,

    @SerializedName("apellidos")
    var apellidos: String,

    @SerializedName("telefono")
    var telefono: String,

    @SerializedName("estado")
    var estado: String,
)
