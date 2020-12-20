package com.mobile.justcleanassignment.service.api

import com.mobile.justcleanassignment.service.modal.CommentsResponse
import com.mobile.justcleanassignment.service.modal.PostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): Response<PostsResponse>

    @GET("/posts/{post_id}/comments")
    suspend fun getCommentsForPost(
        @Path("post_id") postId: Int
    ): Response<CommentsResponse>
}