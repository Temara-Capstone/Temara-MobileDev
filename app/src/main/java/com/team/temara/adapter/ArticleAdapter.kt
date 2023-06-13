import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.ArticleList
import com.team.temara.databinding.ArticleListItemBinding
import com.team.temara.ui.article.ArticleDetailActivity

class ArticleAdapter(private val context: Context) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private val articleList: MutableList<ArticleList> = mutableListOf()

    fun setArticleList(list: ArticleList) {
        articleList.clear()
        articleList.addAll(listOf(list))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.binding.tvTitleArticle.text = article.title
        holder.binding.tvDescArticle.text = article.text

        Glide.with(holder.itemView.context)
            .load(article.image)
            .into(holder.binding.ivContent)

        holder.binding.cvListArticle.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArticleDetailActivity::class.java)
            intent.putExtra(ArticleDetailActivity.TITLE, article.title)
            intent.putExtra(ArticleDetailActivity.DESC, article.text)
            intent.putExtra(ArticleDetailActivity.CREATED_AT, article.createdAt)
            intent.putExtra(ArticleDetailActivity.IMAGE, article.image)
            intent.putExtra(ArticleDetailActivity.UPDATED_AT, article.updatedAt)

            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                Pair(holder.binding.tvTitleArticle, "title"),
                Pair(holder.binding.ivContent, "image"),
                Pair(holder.binding.tvDescArticle, "text"),
            )
            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    override fun getItemCount() = articleList.size

    inner class ViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
