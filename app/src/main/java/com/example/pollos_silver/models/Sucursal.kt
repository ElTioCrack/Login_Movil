package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Sucursal (
    @SerializedName("id_sucursal")
    var id_sucursal: Int,

//    @SerializedName("coordenadas")
    var coordenadas: Coordenadas,

//    @SerializedName("id_ciudad")
    var ciudad: Ciudad,

    @SerializedName("nombre_sucursal")
    var nombre: String,

    @SerializedName("direccion_sucursal")
    var direccion: String,

    @SerializedName("estado_sucursal")
    var estado: Int,

    @SerializedName("telefono_sucursal")
    var telefono: String,

    @SerializedName("hora_apertura_sucursal")
    var hora_apertura: String,

    @SerializedName("hora_cierre_sucursal")
    var hora_cierre: String,
)