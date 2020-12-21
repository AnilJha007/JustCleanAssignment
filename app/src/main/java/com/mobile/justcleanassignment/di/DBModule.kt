package com.mobile.justcleanassignment.di

import android.content.Context
import com.mobile.justcleanassignment.service.database.AppDatabase
import com.mobile.justcleanassignment.service.database.dao.PostDao
import com.mobile.justcleanassignment.service.repository.LocalDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class DBModule {

    @Provides
    fun provideMovieDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).postDao()

    @Provides
    fun provideMovieRepository(postDao: PostDao) = LocalDBRepository(postDao)
}