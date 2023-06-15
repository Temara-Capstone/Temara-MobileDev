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
import com.team.temara.R
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
                        updateData(myToken, myId)
                    }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }


    }

    private fun updateData(token: String, userId: String) {
        val password = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                if (password.length <= 7 ) {
                    Toast.makeText(this, getString(R.string.eight_characters), Toast.LENGTH_SHORT).show()
                } else {
                    passwordViewModel.updatePassword(token, userId, password)
                        .observe(this) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }

                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    val error = result.error
                                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                                    // Show error message
                                }

                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        this,
                                        "Password berhasil diperbarui",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
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