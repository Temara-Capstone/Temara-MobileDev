package com.team.temara.ui.profil.panicbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.PanicButtonActivityBinding

class PanicButtonActivity : AppCompatActivity() {

    private val binding : PanicButtonActivityBinding by lazy {
        PanicButtonActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

}