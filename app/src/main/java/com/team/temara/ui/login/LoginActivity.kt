package com.team.temara.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    private val binding: LoginActivityBinding by lazy {
        LoginActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}