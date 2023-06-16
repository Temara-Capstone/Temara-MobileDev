package com.team.temara.ui.forum.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.ForumCreatePostActivityBinding

class CreatePostActivity : AppCompatActivity() {

    private val binding : ForumCreatePostActivityBinding by lazy {
        ForumCreatePostActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}