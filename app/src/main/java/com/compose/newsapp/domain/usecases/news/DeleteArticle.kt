package com.compose.newsapp.domain.usecases.news

import com.compose.newsapp.data.local.NewsDao
import com.compose.newsapp.domain.model.Article

class DeleteArticle (
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article){
        newsDao.delete(article = article)
    }

}