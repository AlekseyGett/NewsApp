package com.github.alekseygett.newsapp.feature.bookmarks.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.alekseygett.newsapp.R
import com.github.alekseygett.newsapp.databinding.FragmentBookmarksBinding
import com.github.alekseygett.newsapp.feature.article.ui.ArticleActivity
import com.github.alekseygett.newsapp.domain.model.Article
import com.github.alekseygett.newsapp.feature.feed.ui.ArticlesAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarksFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarksFragment()
    }

    private val viewModel: BookmarksViewModel by viewModel()

    private val articlesAdapter: ArticlesAdapter by lazy {
        ArticlesAdapter(
            articles = emptyList(),
            onBookmarkButtonClick = { article ->
                viewModel.processUiEvent(UiEvent.OnBookmarkButtonClick(article))
            },
            onItemClick = { article ->
                openArticleView(article.sourceUrl)
            }
        )
    }

    private var _binding: FragmentBookmarksBinding? = null

    private val binding: FragmentBookmarksBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = articlesAdapter

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processUiEvent(UiEvent.OnCachedArticlesRequest)
    }

    private fun render(viewState: ViewState) {
        articlesAdapter.update(viewState.articles)

        viewState.deletedArticle?.let { deletedArticle ->
            showUndoDialog(deletedArticle)
            viewModel.processUiEvent(UiEvent.OnUndoDialogShowed)
        }
    }

    private fun showUndoDialog(article: Article) {
        Snackbar.make(binding.root, getString(R.string.undo_hint_text), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo_button_text)) {
                viewModel.processUiEvent(UiEvent.OnUndoDeletingButtonClick(article))
            }
            .show()
    }

    private fun openArticleView(url: String) {
        val intent = Intent(context, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.SOURCE_URL_KEY, url)
        startActivity(intent)
    }

}