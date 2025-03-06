package com.compose.newsapp.domain.usecases.news

import com.compose.newsapp.data.local.NewsDao
import com.compose.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<Article>>{
        return newsDao.getArticles()
    }

}