package com.mobile.justcleanassignment.service.api

import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.modal.Post
import retrofit2.Response

interface ApiHelper {

    suspend fun getPosts(): Response<ArrayList<Post>>

    suspend fun getCommentsForPost(postId: Int): Response<ArrayList<Comment>>
}