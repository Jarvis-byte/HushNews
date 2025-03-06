package com.compose.newsapp.data.remote.dto

import com.compose.newsapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)