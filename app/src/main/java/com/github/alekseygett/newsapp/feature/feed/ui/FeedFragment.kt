package com.github.alekseygett.newsapp.feature.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.alekseygett.newsapp.databinding.FragmentFeedBinding
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
            onItemClick = { }
        )
    }

    private var _binding: FragmentFeedBinding? = null

    // Only valid between onCreateView and onDestroyView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = articlesAdapter

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processUiEvent(UiEvent.OnNewsRequest)
    }

    private fun render(viewState: ViewState) {
        binding.progressBar.isVisible = viewState.isLoading

        articlesAdapter.update(viewState.articles)
    }

}