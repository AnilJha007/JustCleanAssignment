package com.mobile.justcleanassignment.service.repository

import com.mobile.justcleanassignment.service.database.dao.PostDao
import com.mobile.justcleanassignment.service.modal.Post
import javax.inject.Inject

class LocalDBRepository @Inject constructor(private val postDao: PostDao) {

    suspend fun insertAllPosts(posts: MutableList<Post>) =
        postDao.insertAllPosts(posts)

    fun getPosts() = postDao.getAllPosts()
}