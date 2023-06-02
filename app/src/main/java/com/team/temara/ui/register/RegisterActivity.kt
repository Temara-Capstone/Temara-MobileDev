package com.team.temara.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.RegisterActivityBinding

class RegisterActivity : AppCompatActivity() {
    private val binding: RegisterActivityBinding by lazy {
        RegisterActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}