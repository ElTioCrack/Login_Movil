package com.example.pollos_silver.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pollos_silver.R
import com.example.pollos_silver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            start_LoginActivity()
        }

        binding.btnRegister.setOnClickListener {
            start_RegisterActivity()
        }
    }

    private fun start_LoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun start_RegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
