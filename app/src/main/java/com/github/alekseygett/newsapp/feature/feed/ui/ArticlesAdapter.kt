package com.github.alekseygett.newsapp.feature.feed.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.alekseygett.newsapp.R
import com.github.alekseygett.newsapp.databinding.ItemArticleBinding
import com.github.alekseygett.newsapp.domain.model.Article
import java.text.SimpleDateFormat
import java.util.*

class ArticlesAdapter(
    private var articles: List<Article>,
    private val onBookmarkButtonClick: (Article) -> Unit,
    private val onItemClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            article: Article,
            onBookmarkButtonClick: (Article) -> Unit,
            onItemClick: (Article) -> Unit
        ) {
            binding.root.setOnClickListener { onItemClick(article) }

            val bookmarkDrawable = getBookmarkDrawable(article.isBookmarked)
            binding.bookmarkButton.setImageDrawable(bookmarkDrawable)
            binding.bookmarkButton.setOnClickListener { onBookmarkButtonClick(article) }

            binding.titleText.text = article.title
            binding.publishedAtText.text = getTimestampString(article.publishedAt)

            if (article.imageUrl == null) {
                binding.previewImage.visibility = View.GONE
            } else {
                binding.previewImage.visibility = View.VISIBLE
                loadPreviewImage(article.imageUrl)
            }

            if (article.description == null) {
                binding.descriptionText.visibility = View.GONE
            } else {
                binding.descriptionText.visibility = View.VISIBLE
                binding.descriptionText.text = article.description
            }
        }

        private fun getBookmarkDrawable(isBookmarked: Boolean): Drawable? {
            val context = binding.root.context

            val bookmarkDrawableResId = if (isBookmarked) {
                R.drawable.bookmark_checked_icon
            } else {
                R.drawable.bookmark_unchecked_icon
            }

            return ResourcesCompat.getDrawable(
                context.resources,
                bookmarkDrawableResId,
                null
            )
        }

        @SuppressLint("SimpleDateFormat")
        private fun getTimestampString(date: Date): String =
            SimpleDateFormat("dd.MM.yyyy HH:mm").run { format(date) }

        private fun loadPreviewImage(imageUrl: String) {
            val context = binding.root.context

            val placeholderDrawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.image_icon,
                null
            )

            val errorDrawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.broken_image_icon,
                null
            )

            Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .into(binding.previewImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position], onBookmarkButtonClick, onItemClick)
    }

    override fun getItemCount(): Int = articles.count()

    fun update(data: List<Article>) {
        articles = data
        notifyDataSetChanged()
    }

}