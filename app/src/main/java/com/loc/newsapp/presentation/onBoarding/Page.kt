package com.loc.newsapp.presentation.onBoarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

data class Page(
    val title: String, val description: String,
    @DrawableRes
    val image: Int,
)

val pages = listOf(
    Page(
        title = "Your new reading experience",
        description = "Your email articles,skimmable." +
                "All in one place",

        image = R.drawable.onboarding1
    ),
    Page(
        title = "Personalize",
        description = "Improve your reading experience by following your favourite digests,publication and interests",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Save article for later",
        description = "Hit the bookmark icon on your article to save for later",
        image = R.drawable.onboarding3
    )
)