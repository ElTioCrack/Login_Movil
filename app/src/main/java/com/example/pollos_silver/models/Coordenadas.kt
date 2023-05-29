package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Coordenadas(
    @SerializedName("id_coordenada")
    var id_coordenada : Int,

    @SerializedName("latitud_coordenada")
    var latitud : Float,

    @SerializedName("longitud_coordenada")
    var longitud : Float,

    @SerializedName("fecha_hora_coordenada")
    var fecha_hora: String,
)
//{
//    var fecha_hora: LocalDateTime
//
//    init {
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        fecha_hora = LocalDateTime.parse(dateTime, formatter)
//    }
//}