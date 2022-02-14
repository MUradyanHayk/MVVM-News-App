package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.NewsActivity
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.data.NewsResponse
import com.example.myapplication.utils.NewsResource
import com.example.myapplication.viewModel.NewsViewModel

class BreakingNewsFragment : Fragment() {
    val TAG = "BreakingNewsFragment"
    private var screen: View? = null
    private var progressBar: ProgressBar? = null
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        screen = inflater.inflate(R.layout.fragment_breaking_news, container, false)
        progressBar = screen?.findViewById(R.id.progress_bar_id)
        return screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NewsResource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toMutableList())
                    }
                }
                is NewsResource.Error -> {
                    hideProgressBar()
//                    response.?. let {
//                        newsAdapter.differ.submitList(it.articles)
//                    }
                }
                is NewsResource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        val recyclerView = screen?.findViewById<RecyclerView>(R.id.recyclerView_id)
        newsAdapter = NewsAdapter()
        recyclerView?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

    }
}