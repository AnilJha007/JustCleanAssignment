package com.mobile.justcleanassignment.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.justcleanassignment.service.modal.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPosts(posts: MutableList<Post>): List<Long>

    @Query("SELECT * FROM post_list")
    fun getAllPosts(): LiveData<MutableList<Post>>

    @Query("UPDATE post_list SET isFavorite = :isFav WHERE id = :postId")
    suspend fun updatePost(postId: Int, isFav: Boolean): Int

    @Query("SELECT * FROM post_list WHERE isFavorite = 1")
    fun getAllFavPosts(): LiveData<MutableList<Post>>
}