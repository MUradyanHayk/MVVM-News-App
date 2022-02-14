package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.db.ArticleDatabase
import com.example.myapplication.fragment.BreakingNewsFragment
import com.example.myapplication.fragment.SavedNewsFragment
import com.example.myapplication.fragment.SearchNewsFragment
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.viewModel.NewsViewModel
import com.example.myapplication.viewModel.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModeProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModeProviderFactory)[NewsViewModel::class.java]
//        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        updateFragment(BreakingNewsFragment())
        initBottomView()
    }

    private fun initBottomView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.breakingNewsFragment -> {
                    updateFragment(BreakingNewsFragment())
                }
                R.id.savedNewsFragment -> {
                    updateFragment(SavedNewsFragment())
                }
                R.id.searchNewsFragment -> {
                    updateFragment(SearchNewsFragment())
                }
//                R.id.breakingNewsFragment -> {}
            }

            true
        }
    }

    private fun updateFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}