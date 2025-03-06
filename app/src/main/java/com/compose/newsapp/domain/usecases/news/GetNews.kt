package com.compose.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.compose.newsapp.data.repository.NewsRepository
import com.compose.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}