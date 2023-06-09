package com.team.temara.ui.profile.personalData

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.PersonalDataActivityBinding

class PersonalDataActivity : AppCompatActivity() {
    private val binding: PersonalDataActivityBinding by lazy {
        PersonalDataActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}