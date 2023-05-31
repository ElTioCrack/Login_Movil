package com.example.pollos_silver.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pollos_silver.MyApplication
import com.example.pollos_silver.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            binding.txtTest.text = MyApplication.Usuario.toString()
        } catch (e: Exception) {}

        binding.btnTest.setOnClickListener {
            MyApplication.SharedPreferences.clear()
            finishAffinity()
            // Inicia la Actividad 1
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}