package com.team.temara.ui.forum.inpost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.ForumPostActivityBinding

class ForumPostActivity : AppCompatActivity() {

    private val binding: ForumPostActivityBinding by lazy {
        ForumPostActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}