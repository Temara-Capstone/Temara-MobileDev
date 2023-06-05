package com.team.temara.ui.main.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.ChatFragmentBinding

class ChatFragment : AppCompatActivity() {

    private val binding : ChatFragmentBinding by lazy {
        ChatFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}