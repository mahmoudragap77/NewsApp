package com.example.thenewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.newsprojectpractice.R
import com.example.thenewsapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var articleImage: ImageView
        lateinit var articleSource: TextView
        lateinit var articleTitle: TextView
        lateinit var articleDescription: TextView
        lateinit var articleDateTime: TextView
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListner: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.apply {
            articleImage = itemView.findViewById(R.id.articleImage)
            articleSource = itemView.findViewById(R.id.articleSource)
            articleTitle = itemView.findViewById(R.id.articleTitle)
            articleDescription = itemView.findViewById(R.id.articleDescription)
            articleDateTime = itemView.findViewById(R.id.articleDateTime)

            holder.itemView.apply {
                Glide.with(this).load(article.urlToImage).into(articleImage)
                articleSource.text = article.source?.name
                articleTitle.text = article.title
                articleDescription.text = article.description
                articleDateTime.text = article.publishedAt

                setOnClickListener {
                    onItemClickListner?.let {
                        it(article)
                    }
                }
            }
        }

    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListner = listener
    }

}