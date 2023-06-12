package com.example.register.networks

import android.app.AlertDialog
import android.content.Context
import com.example.pollos_silver.models.ErrorResponse
import com.example.register.Interfaces.LoginService
import com.example.register.Models.Cliente
import com.example.register.Models.Empleado
import com.example.register.Models.Usuario
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginApiManager(private val loginService: LoginService, private val context: Context) {

    fun authenticate(ci: Int, password: String, onResponse: (Usuario?) -> Unit) {
        makeApiCall(loginService.authenticate(ci, password)) { user ->
            onResponse(user)
        }
    }

    fun login_empleado(ci: Int, password: String, onResponse: (Empleado?) -> Unit) {
        makeApiCall(loginService.login_empleado(ci, password, "App")) { empleado ->
            onResponse(empleado)
        }
    }

    fun login_cliente(ci: Int, password: String, onResponse: (Cliente?) -> Unit) {
        makeApiCall(loginService.login_cliente(ci, password)) { cliente ->
            onResponse(cliente)
        }
    }

    private fun <T> makeApiCall(call: Call<T>, onResponse: (T?) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    if(response !== null) {
                        onResponse(response.body())
                    } else {
                        showMessageDialog("", "Respuesta Vacia")
                    }
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    showMessageDialog(errorResponse.errorCode.toString(), errorResponse.errorMessage)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                showMessageDialog("onFailure", t.message.toString())
            }
        })
    }

    private fun showMessageDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("ERROR: ${title}")
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}

