package com.mobile.justcleanassignment.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.justcleanassignment.service.modal.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(movies: MutableList<Post>): List<Long>

    @Query("SELECT * FROM post_list")
    fun getAllPosts(): LiveData<MutableList<Post>>
}