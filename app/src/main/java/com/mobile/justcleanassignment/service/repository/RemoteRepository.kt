package com.mobile.justcleanassignment.service.repository

import com.mobile.justcleanassignment.service.api.ApiHelper
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getPosts() = apiHelper.getPosts()

    suspend fun getCommentsForPost(postId: Int) = apiHelper.getCommentsForPost(postId)
}