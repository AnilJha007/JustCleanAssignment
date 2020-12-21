package com.mobile.justcleanassignment.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.justcleanassignment.service.modal.Comment

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(comments: MutableList<Comment>): List<Long>

    @Query("SELECT * FROM comment_list where postId = :postId")
    fun getCommentsForPost(postId: Int): LiveData<MutableList<Comment>>
}