package com.github.alekseygett.newsapp.feature.article.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.alekseygett.newsapp.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    companion object {
        const val SOURCE_URL_KEY = "SOURCE_URL"
    }

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(SOURCE_URL_KEY)

        setupView(url)
    }

    private fun setupView(url: String?) {
        setSupportActionBar(binding.appBar)

        binding.appBar.setNavigationOnClickListener(::onNavigationButtonClick)

        binding.contentWebView.settings.javaScriptEnabled = true
        binding.contentWebView.webViewClient = object : WebViewClient() {}

        url?.let {
            binding.appBar.title = it
            binding.contentWebView.loadUrl(it)
        }
    }

    private fun onNavigationButtonClick(view: View) {
        onBackPressed()
    }

}