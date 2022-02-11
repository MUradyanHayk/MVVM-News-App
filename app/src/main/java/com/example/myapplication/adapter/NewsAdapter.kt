package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview, parent, false)
        return ArticleViewHolder(item)

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.configure(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var _onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        _onItemClickListener = listener
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var articleImageView: ImageView? = null
        private var sourceTextView: TextView? = null
        private var publishedAtTextView: TextView? = null
        private var titleTextView: TextView? = null
        private var descriptionTextView: TextView? = null

        init {
            articleImageView = itemView.findViewById(R.id.articleImageView)
            sourceTextView = itemView.findViewById(R.id.sourceTextView)
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView)
            titleTextView = itemView.findViewById(R.id.titleTextView)
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView)
        }

        fun configure(article: Article) {
            Glide.with(itemView).load(article.urlToImage).into(articleImageView!!)
            sourceTextView?.text = article.source.name
            titleTextView?.text = article.title
            descriptionTextView?.text = article.description
            publishedAtTextView?.text = article.publishedAt

            itemView. setOnClickListener {
                _onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

}