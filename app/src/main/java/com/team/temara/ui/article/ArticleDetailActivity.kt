package com.team.temara.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.databinding.ArticleDetailActivityBinding

class ArticleDetailActivity : AppCompatActivity() {

    private val binding: ArticleDetailActivityBinding by lazy {
        ArticleDetailActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    companion object {
        const val TITLE = "title_extra"
        const val DESC = "desc_extra"
        const val IMAGE = "image_extra"
        const val CREATED_AT = "created_at_extra"
        const val UPDATED_AT = "updated_at_extra"
    }
}