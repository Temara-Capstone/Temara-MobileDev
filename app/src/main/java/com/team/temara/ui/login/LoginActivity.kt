package com.team.temara.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    private val binding: LoginActivityBinding by lazy {
        LoginActivityBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.LoginViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {

    }
}