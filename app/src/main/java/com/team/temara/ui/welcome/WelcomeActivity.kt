package com.team.temara.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.WelcomeActivityBinding
import com.team.temara.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    private val binding : WelcomeActivityBinding by lazy {
        WelcomeActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}