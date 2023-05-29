package com.example.pollos_silver.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pollos_silver.R
import com.example.pollos_silver.databinding.ActivityLoginBinding
import com.example.register.Interfaces.LoginService
import com.example.register.Models.UserLoginInfo
import com.example.register.networks.LoginApiManager
import com.example.register.networks.RetrofitInstance

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginApiManager: LoginApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = RetrofitInstance.getRetrofitInstance()
        val loginService = retrofit.create(LoginService::class.java)
        loginApiManager = LoginApiManager(loginService, this)

        binding.btnLogin.setOnClickListener {
            val ci = binding.txtCi.text.toString().toInt()
            val password = binding.txtPassword.text.toString()
            authenticate(ci, password)
        }
    }
    private fun authenticate(ci: Int, password: String) {
        loginApiManager.authenticate(ci, password) { usuario ->
            usuario?.let {
                when (it.tipousuario.tipo) {
                    "Jefe" -> showToast("¡ES EL J3FE!\nD:")
                    "Empleado" -> login_empleado(ci, password)
                    "Cliente" -> login_cliente(ci, password)
                    else -> showToast("¡Acceso no autorizado!")
                }
            } ?: showToast("El usuario no existe o los datos son incorrectos")
        }
    }

    private fun login_empleado(ci: Int, password: String) {
        loginApiManager.login_empleado(ci, password) { empleado ->
            empleado?.let {
                UserLoginInfo.usuario = it
                iniciarSecundaryActivity()
            } ?: showToast("La respuesta está vacía")
        }
    }

    private fun login_cliente(ci: Int, password: String) {
        loginApiManager.login_cliente(ci, password) { cliente ->
            cliente?.let {
                UserLoginInfo.usuario = it
                iniciarSecundaryActivity()
            }
        }
    }

    private fun iniciarSecundaryActivity() {
        showToast("Inicio de sesión exitoso")
//        val intent = Intent(this, SecundaryActivity::class.java)
//        startActivity(intent)
    }

    private fun showToast(text: String?) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}