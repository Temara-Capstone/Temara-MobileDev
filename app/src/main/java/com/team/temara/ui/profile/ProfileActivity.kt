package com.team.temara.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.ProfileActivityBinding

class ProfileActivity : AppCompatActivity() {
    private val binding: ProfileActivityBinding by lazy {
        ProfileActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}