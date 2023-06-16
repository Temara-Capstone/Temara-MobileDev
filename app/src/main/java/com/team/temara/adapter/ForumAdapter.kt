package com.team.temara.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.ForumList
import com.team.temara.databinding.ForumPostItemBinding
import com.team.temara.ui.article.ArticleDetailActivity
import com.team.temara.utils.DateFormatter
import java.util.TimeZone

class ForumAdapter (private val context: Context) : RecyclerView.Adapter<ForumAdapter.ViewHolder>() {
    private val forumList: MutableList<ForumList> = mutableListOf()

    fun setForumList(list: List<ForumList>) {
        forumList.clear()
        forumList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ForumPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forum = forumList[position]
        holder.binding.tvCreatedAt.text =
            forum.createdAt.let { DateFormatter.formaArticletDate(it, TimeZone.getDefault().id) }
        holder.binding.tvDescForum.text = forum.text

        if (forum.images == "null") {
            holder.binding.ivDesc.visibility = View.GONE
            holder.binding.cvIvDesc.visibility = View.GONE
        } else {
            holder.binding.cvIvDesc.visibility = View.VISIBLE
            holder.binding.ivDesc.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(forum.images)
                .into(holder.binding.ivDesc)
        }

        holder.binding.cvForumPost.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArticleDetailActivity::class.java).apply {
                putExtra(ArticleDetailActivity.ARTICLE_DETAIL_EXTRA, forum)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = forumList.size

    inner class ViewHolder(val binding: ForumPostItemBinding) : RecyclerView.ViewHolder(binding.root)
}