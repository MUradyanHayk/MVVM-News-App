package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.fragment.BreakingNewsFragment
import com.example.myapplication.fragment.SavedNewsFragment
import com.example.myapplication.fragment.SearchNewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

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