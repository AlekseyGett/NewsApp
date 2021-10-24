package com.github.alekseygett.newsapp.feature.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.alekseygett.newsapp.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private var _binding: FragmentFeedBinding? = null

    // Only valid between onCreateView and onDestroyView
    private val binding
        get() = _binding!!

    private val articlesAdapter: ArticlesAdapter by lazy {
        ArticlesAdapter(
            articles = emptyList(),
            onBookmarkButtonClick = { },
            onItemClick = { }
        )
    }

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
    }
}