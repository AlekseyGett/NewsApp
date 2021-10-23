package com.github.alekseygett.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.alekseygett.newsapp.databinding.ActivityMainBinding
import com.github.alekseygett.newsapp.feature.bookmarks.ui.BookmarksFragment
import com.github.alekseygett.newsapp.feature.feed.ui.FeedFragment
import com.github.alekseygett.newsapp.feature.search.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        setupView()
    }

    private fun setupView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feed -> navigateTo(FeedFragment.newInstance())
                R.id.search -> navigateTo(SearchFragment.newInstance())
                R.id.bookmarks -> navigateTo(BookmarksFragment.newInstance())
            }

            true
        }

        binding.bottomNavigation.selectedItemId = R.id.feed
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}