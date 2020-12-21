package com.mobile.justcleanassignment.service.modal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "post_list")
@Parcelize
data class Post(
    val userId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    var isFavorite: Boolean = false
) : Parcelable