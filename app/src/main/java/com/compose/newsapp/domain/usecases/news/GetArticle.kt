package com.compose.newsapp.domain.usecases.news

import com.compose.newsapp.data.local.NewsDao
import com.compose.newsapp.domain.model.Article

class GetArticle (
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(url: String): Article?{
        return newsDao.getArticle(url = url)
    }

}