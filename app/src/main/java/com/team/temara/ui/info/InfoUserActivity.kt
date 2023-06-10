package com.team.temara.ui.info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.InfoUserActivityBinding

class InfoUserActivity : AppCompatActivity() {

    private val binding : InfoUserActivityBinding by lazy {
        InfoUserActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

}