package com.team.temara.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.R
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.LoginActivityBinding
import com.team.temara.ui.main.MainActivity
import com.team.temara.ui.register.RegisterActivity

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
        binding.btnLogin.setOnClickListener {
            if(!binding.etEmail.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                if(password.length <= 7) {
                    Toast.makeText(this, getString(R.string.password_minimum), Toast.LENGTH_SHORT).show()
                } else {
                    loginViewModel.login(email, password).observe(this) {
                        when (it) {
                            is Result.Success -> {
                                binding.ProgressBar.visibility = View.GONE
                                val data = it.data
                                loginViewModel.setUserToken(data.data.token)
                                Log.d("Login Activity", "token: ${data.data.token}")
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            is Result.Error -> {
                                binding.ProgressBar.visibility = View.GONE
                                val error = it.error
                                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                            }

                            is Result.Loading -> {
                                binding.ProgressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            } else {
                binding.etEmail.error = resources.getString(R.string.email_empty)
                if(binding.etPassword.text.isNullOrEmpty()) {
                    binding.etPassword.error = getString(R.string.password_empty)
                }
            }
        }

        binding.btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}