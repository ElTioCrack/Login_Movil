package com.example.register.networks

import android.content.Context
import android.widget.Toast
import com.example.register.Interfaces.LoginService
import com.example.register.Models.Cliente
import com.example.register.Models.Empleado
import com.example.register.Models.Usuario
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
                        showToast("response: null\n${response}")
                    }
                } else {
                    showToast("!isSuccessful:\n${response.code()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                showToast("onFailure:\n${t.message}")
            }
        })
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}