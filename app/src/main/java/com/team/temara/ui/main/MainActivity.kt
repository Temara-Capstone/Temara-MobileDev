package com.team.temara.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private val binding : MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}