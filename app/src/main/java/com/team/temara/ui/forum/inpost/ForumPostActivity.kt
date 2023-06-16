package com.team.temara.ui.forum.inpost

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.ForumList
import com.team.temara.databinding.ForumPostActivityBinding
import com.team.temara.utils.DateFormatter
import java.util.TimeZone

class ForumPostActivity : AppCompatActivity() {

    private val binding: ForumPostActivityBinding by lazy {
        ForumPostActivityBinding.inflate(layoutInflater)
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
        val forum = intent.getParcelableExtra<ForumList>(FORUM_DETAIL_EXTRA)

        val createdAt = forum?.createdAt.let { DateFormatter.formaArticletDate(it, TimeZone.getDefault().id) }
        val images = forum?.images
        val desc = forum?.text

        binding.tvDescForum.text = desc
        binding.tvCreatedAt.text = createdAt

        if (images == null || images == "null") {
            binding.ivDesc.visibility = View.GONE
            binding.cvIvDesc.visibility = View.GONE
        } else {
            binding.ivDesc.visibility = View.VISIBLE
            binding.cvIvDesc.visibility = View.VISIBLE
            Glide.with(this)
                .load(images)
                .into(binding.ivDesc)
        }
    }

    companion object {
        const val FORUM_DETAIL_EXTRA = "forum_detal_extra"
    }
}