package com.compose.newsapp.presentation.bookmark


import com.compose.newsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)