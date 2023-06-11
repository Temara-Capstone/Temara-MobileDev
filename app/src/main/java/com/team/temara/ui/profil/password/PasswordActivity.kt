package com.team.temara.ui.profil.password

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.PasswordActivityBinding

class PasswordActivity : AppCompatActivity() {

    private val binding : PasswordActivityBinding by lazy {
        PasswordActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

}