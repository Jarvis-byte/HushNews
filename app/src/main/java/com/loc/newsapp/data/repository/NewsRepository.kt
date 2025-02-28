package com.loc.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.loc.newsapp.data.local.NewsDao
import com.loc.newsapp.data.remote.NewsApi
import com.loc.newsapp.data.remote.NewsPagingSource
import com.loc.newsapp.data.remote.SearchNewsPagingSource
import com.loc.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(val newsApi: NewsApi, private val newsDao: NewsDao) {
    fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(newsApi = newsApi, sources = sources.joinToString(separator = ","))
            }
        ).flow
    }

    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    api = newsApi,
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    suspend fun getArticle(url: String): Article? {
        return newsDao.getArticle(url = url)
    }
}