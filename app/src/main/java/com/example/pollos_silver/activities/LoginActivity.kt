package com.example.pollos_silver.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pollos_silver.MyApplication
import com.example.pollos_silver.databinding.ActivityLoginBinding
import com.example.register.Interfaces.LoginService
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

        // button
        binding.btnLogin.setOnClickListener {
            val ci = binding.txtCi.text.toString().toIntOrNull()
            val password = binding.txtPassword.text.toString()

            if (ci != null && password.isNotEmpty()) {
                authenticate(ci, password)
            } else {
                showToast("Ingresa un CI válido y una contraseña")
            }
        }
    }
    private fun authenticate(ci: Int, password: String) {
        loginApiManager.authenticate(ci, password) { usuario ->
            usuario?.let {
                when (it.tipousuario.tipo) {
                    "Jefe" -> showToast("¡ES EL J3FE!:")
                    "Empleado" -> login_empleado(ci, password)
                    "Cliente" -> login_cliente(ci, password)
                    else -> showToast("¡Acceso no autorizado!")
                }
            }
        }
    }

    private fun login_empleado(ci: Int, password: String) {
        loginApiManager.login_empleado(ci, password) { empleado ->
            empleado?.let {
                MyApplication.Usuario = it
                MyApplication.SharedPreferences.save_ci(ci)
                MyApplication.SharedPreferences.save_contrasena(password)
                MyApplication.SharedPreferences.save_tipo_usuario(it.usuario.tipousuario.tipo)
                start_()
            }
        }
    }

    private fun login_cliente(ci: Int, password: String) {
        loginApiManager.login_cliente(ci, password) { cliente ->
            cliente?.let {
                MyApplication.Usuario = it
                MyApplication.SharedPreferences.save_ci(ci)
                MyApplication.SharedPreferences.save_contrasena(password)
                MyApplication.SharedPreferences.save_tipo_usuario(it.usuario.tipousuario.tipo)
                start_()
            }
        }
    }

    private fun start_() {
        startActivity(Intent(this, TestActivity::class.java))
        finish()
    }

    private fun showToast(text: String?) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}