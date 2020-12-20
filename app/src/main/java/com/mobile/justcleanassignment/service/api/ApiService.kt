package com.mobile.justcleanassignment.service.api

import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.modal.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): Response<ArrayList<Post>>

    @GET("/posts/{post_id}/comments")
    suspend fun getCommentsForPost(
        @Path("post_id") postId: Int
    ): Response<ArrayList<Comment>>
}