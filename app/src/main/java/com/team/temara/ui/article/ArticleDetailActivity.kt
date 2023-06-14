package com.team.temara.ui.article

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.ArticleList
import com.team.temara.databinding.ArticleDetailActivityBinding
import com.team.temara.utils.DateFormatter
import java.util.TimeZone

class ArticleDetailActivity : AppCompatActivity() {

    private val binding: ArticleDetailActivityBinding by lazy {
        ArticleDetailActivityBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setup()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setup() {
        val article = intent.getParcelableExtra<ArticleList>(ARTICLE_DETAIL_EXTRA)

        val title = article?.title
        val image = article?.image
        val desc = article?.text
        val createdAt = article?.createdAt?.let { DateFormatter.formaArticletDate(it, TimeZone.getDefault().id) }
        val updatedAt = article?.updatedAt?.let { DateFormatter.formaArticletDate(it, TimeZone.getDefault().id) }

        binding.tvTitleArticle.text = title
        binding.tvDescArticle.text = desc
        binding.tvCreatedAt.text = createdAt
        binding.tvUpdatedAt.text = updatedAt

        Glide.with(this)
            .load(image)
            .into(binding.ivArticle)
    }


    companion object {
        const val ARTICLE_DETAIL_EXTRA = "article_detail_extra"
    }
}