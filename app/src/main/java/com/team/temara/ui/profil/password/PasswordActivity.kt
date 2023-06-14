package com.team.temara.ui.profil.password

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.PasswordActivityBinding
import com.team.temara.ui.login.LoginActivity

class PasswordActivity : AppCompatActivity() {

    private val binding: PasswordActivityBinding by lazy {
        PasswordActivityBinding.inflate(layoutInflater)
    }

    private val passwordViewModel: PasswordViewModel by viewModels {
        PasswordViewModel.PasswordViewModelFactory.getInstance(this)
    }

    private var isDataLoaded = false



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            passwordViewModel.checkToken().observe(this) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    passwordViewModel.checkId().observe(this) { userId ->
                        val myId = userId ?: ""
                        updateData(myId)
                    }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }


        if (!isDataLoaded) {
            passwordViewModel.checkToken().observe(this) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    passwordViewModel.checkId().observe(this) { userId ->
                        val myId = userId ?: ""

                        passwordViewModel.getUser(myToken, myId).observe(this) {
                            when (it) {
                                is Result.Loading -> {
                                }

                                is Result.Error -> {
                                }

                                is Result.Success -> {
                                    val result = it.result

                                    binding.etNewPassword.text = Editable.Factory.getInstance().newEditable(result.name)
                                    binding.etConfirmPassword.text = Editable.Factory.getInstance().newEditable(result.name)
                                }
                            }
                        }
                    }
                }
                isDataLoaded = true
            }
        }

    }

    override fun onResume() {
        super.onResume()
        isDataLoaded = false
    }

    private fun updateData(userId: String) {
        val password = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                passwordViewModel.updatePassword(userId, password).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            // Show error message
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }
}