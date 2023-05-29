package com.example.register.Models

import com.google.gson.annotations.SerializedName

data class Ciudad(
    @SerializedName("id_ciudad")
    var id_ciudad: Int,

    @SerializedName("nombre_ciudad")
    var nombre: String,
)
