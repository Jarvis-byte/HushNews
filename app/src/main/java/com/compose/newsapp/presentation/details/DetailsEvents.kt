package com.compose.newsapp.presentation.details

import com.compose.newsapp.domain.model.Article


sealed class DetailsEvent {

    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()


}