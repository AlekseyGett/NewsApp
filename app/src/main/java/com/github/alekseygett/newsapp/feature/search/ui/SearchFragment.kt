package com.github.alekseygett.newsapp.feature.search.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.alekseygett.newsapp.R
import com.github.alekseygett.newsapp.databinding.FragmentSearchBinding
import com.github.alekseygett.newsapp.feature.article.ui.ArticleActivity
import com.github.alekseygett.newsapp.adapters.ArticlesAdapter
import com.github.alekseygett.newsapp.utils.setDebouncingTextListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModel()

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

    private var _binding: FragmentSearchBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.toolbarSearch -> {
                    viewModel.processUiEvent(UiEvent.OnSearchButtonClick)
                    true
                }
                else -> false
            }
        }

        binding.searchField.setDebouncingTextListener { text ->
            viewModel.processUiEvent(UiEvent.OnNewsRequest(text))
        }

        binding.recyclerView.adapter = articlesAdapter

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processUiEvent(UiEvent.OnNewsRequest(""))
    }

    private fun render(viewState: ViewState) {
        binding.searchFieldWrapper.isGone = !viewState.isSearchFieldVisible
        binding.progressBar.isGone = !viewState.isLoading

        articlesAdapter.update(viewState.articles)

        viewState.errorMessage?.let { errorMessage ->
            showErrorMessage(errorMessage)
            viewModel.processUiEvent(UiEvent.OnErrorMessageShow)
        }
    }

    private fun openArticleView(url: String) {
        val intent = Intent(context, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.SOURCE_URL_KEY, url)
        startActivity(intent)
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

}