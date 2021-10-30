package com.github.alekseygett.newsapp.feature.feed.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.alekseygett.newsapp.databinding.FragmentFeedBinding
import com.github.alekseygett.newsapp.feature.article.ui.ArticleActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private val viewModel: FeedViewModel by viewModel()

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

    private var _binding: FragmentFeedBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
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
        viewModel.processUiEvent(UiEvent.OnNewsRequest)
    }

    private fun render(viewState: ViewState) {
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