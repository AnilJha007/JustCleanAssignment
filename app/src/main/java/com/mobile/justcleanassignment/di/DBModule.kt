package com.mobile.justcleanassignment.di

import android.content.Context
import com.mobile.justcleanassignment.service.database.AppDatabase
import com.mobile.justcleanassignment.service.database.dao.CommentDao
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
    fun providePostDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).postDao()

    @Provides
    fun provideCommentDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).commentDao()

    @Provides
    fun provideLocalDBRepository(postDao: PostDao, commentDao: CommentDao) =
        LocalDBRepository(postDao, commentDao)
}