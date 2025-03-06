package com.compose.newsapp.di

import android.app.Application
import androidx.room.Room
import com.compose.newsapp.data.local.NewsDao
import com.compose.newsapp.data.local.NewsDatabase
import com.compose.newsapp.data.local.NewsTypeConvertor
import com.compose.newsapp.data.manager.LocalUserMangerImpl

import com.compose.newsapp.data.remote.NewsApi
import com.compose.newsapp.data.repository.NewsRepository
import com.compose.newsapp.domain.manager.LocalUserManger

import com.compose.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.compose.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.compose.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.compose.newsapp.domain.usecases.news.DeleteArticle
import com.compose.newsapp.domain.usecases.news.GetArticle
import com.compose.newsapp.domain.usecases.news.GetArticles
import com.compose.newsapp.domain.usecases.news.GetNews
import com.compose.newsapp.domain.usecases.news.NewsUseCases
import com.compose.newsapp.domain.usecases.news.SearchNews
import com.compose.newsapp.domain.usecases.news.UpsertArticle
import com.compose.newsapp.utils.Constants.BASE_URL

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application,
    ): LocalUserManger = LocalUserMangerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger,
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger),
    )

    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao,
    ): NewsRepository {
        return NewsRepository(newsApi, newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao,
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao),
            deleteArticle = DeleteArticle(newsDao),
            getArticles = GetArticles(newsDao),
            getArticle = GetArticle(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application,
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase,
    ): NewsDao = newsDatabase.newsDao

}