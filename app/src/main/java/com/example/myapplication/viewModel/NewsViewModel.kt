package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.NewsRepository

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

}