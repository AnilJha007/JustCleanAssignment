package com.mobile.justcleanassignment.service.repository

import com.mobile.justcleanassignment.service.database.dao.CommentDao
import com.mobile.justcleanassignment.service.database.dao.PostDao
import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.modal.Post
import javax.inject.Inject

class LocalDBRepository @Inject constructor(
    private val postDao: PostDao,
    private val commentDao: CommentDao
) {

    suspend fun insertAllPosts(posts: MutableList<Post>) =
        postDao.insertAllPosts(posts)

    fun getPosts() = postDao.getAllPosts()

    fun getAllFavPosts() = postDao.getAllFavPosts()

    suspend fun updatePost(postId: Int, isFav: Boolean) = postDao.updatePost(postId, isFav)

    suspend fun insertAllComments(comments: MutableList<Comment>) =
        commentDao.insertAllComments(comments)

    fun getCommentsFroPost(postId: Int) = commentDao.getCommentsForPost(postId)
}