package com.team.temara.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.team.temara.data.repository.AuthRepository
import com.team.temara.databinding.SplashActivityBinding
import com.team.temara.ui.login.LoginActivity
import com.team.temara.ui.main.MainActivity
import com.team.temara.ui.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    private val binding : SplashActivityBinding by lazy {
        SplashActivityBinding.inflate(layoutInflater)
    }

    private val splashViewModel: SplashViewModel by viewModels {
        SplashViewModel.SplashViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            checkSession()
        }, 3000)

    }

    private fun checkSession() {
        splashViewModel.checkToken().observe(this) {
            if (it == "null") {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}