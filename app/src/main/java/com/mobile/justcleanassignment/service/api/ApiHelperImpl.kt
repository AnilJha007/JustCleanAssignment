package com.mobile.justcleanassignment.service.api

import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.modal.Post
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPosts(): Response<ArrayList<Post>> = apiService.getPosts()

    override suspend fun getCommentsForPost(postId: Int): Response<ArrayList<Comment>> =
        apiService.getCommentsForPost(postId)
}