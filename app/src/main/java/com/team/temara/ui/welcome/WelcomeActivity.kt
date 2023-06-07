package com.team.temara.ui.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.R
import com.team.temara.databinding.WelcomeActivityBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding : WelcomeActivityBinding by lazy {
        WelcomeActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}