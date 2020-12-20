package com.mobile.justcleanassignment.service.api

import com.mobile.justcleanassignment.service.modal.CommentsResponse
import com.mobile.justcleanassignment.service.modal.PostsResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPosts(): Response<PostsResponse> = apiService.getPosts()

    override suspend fun getCommentsForPost(postId: Int): Response<CommentsResponse> =
        apiService.getCommentsForPost(postId)
}