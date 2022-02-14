package com.example.myapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.NewsResponse
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.utils.NewsResource
import kotlinx.coroutines.launch
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<NewsResource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String)  = viewModelScope.launch {
        breakingNews.postValue(NewsResource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):NewsResource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return NewsResource.Success(it)
            }
        }
        return NewsResource.Error(response.message())
    }

}