package com.mobile.justcleanassignment.service.api

import com.mobile.justcleanassignment.service.modal.CommentsResponse
import com.mobile.justcleanassignment.service.modal.PostsResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getPosts(): Response<PostsResponse>

    suspend fun getCommentsForPost(postId: Int): Response<CommentsResponse>
}