package com.team.temara.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.R
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.RegisterActivityBinding
import com.team.temara.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private val binding: RegisterActivityBinding by lazy {
        RegisterActivityBinding.inflate(layoutInflater)
    }

    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModel.RegisterViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewSetup()
    }

    private fun viewSetup() {
        supportActionBar?.hide()
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            if (!name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                val result = registerViewModel.registerUsers(name.toString(), email.toString(), password.toString())

                if(password.length <= 7) {
                    Toast.makeText(this, getString(R.string.password_minimum), Toast.LENGTH_SHORT).show()
                } else {
                    result.observe(this) {
                        when (it) {
                            is Result.Success -> {
                                binding.ProgressBar.visibility = View.GONE
                                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            is Result.Error -> {
                                binding.ProgressBar.visibility = View.GONE
                                val error = it.error
                                when {
                                    error.contains("HTTP 401") -> {
                                        Toast.makeText(
                                            this,
                                            getString(R.string.error_401),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    error.contains("HTTP 500") -> {
                                        Toast.makeText(
                                            this,
                                            getString(R.string.error_500),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    else -> {
                                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            is Result.Loading -> {
                                binding.ProgressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            } else {
                if (name.isNullOrEmpty()) binding.etName.error = getString(R.string.name_empty)
                if (email.isNullOrEmpty()) binding.etEmail.error = getString(R.string.email_empty)
                if (password.isNullOrEmpty()) binding.etPassword.error = getString(R.string.password_empty)
            }
        }

        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}