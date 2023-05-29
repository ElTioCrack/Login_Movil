package com.example.register.Interfaces

import com.example.register.Models.Cliente
import com.example.register.Models.Empleado
import com.example.register.Models.Usuario
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("Login/PHP/authenticate.php")
    fun authenticate(
        @Field("ci") ci: Int,
        @Field("password") password: String
    ): Call<Usuario>

    @FormUrlEncoded
    @POST("Login/PHP/login_empleado.php")
    fun login_empleado(
        @Field("ci") ci: Int,
        @Field("password") password: String,
        @Field("system") system: String
    ): Call<Empleado>

    @FormUrlEncoded
    @POST("Login/PHP/login_cliente.php")
    fun login_cliente(
        @Field("ci") ci: Int,
        @Field("password") password: String
    ): Call<Cliente>
}


