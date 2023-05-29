package com.example.pollos_silver.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pollos_silver.R
import com.example.pollos_silver.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}