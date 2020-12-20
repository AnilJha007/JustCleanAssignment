package com.mobile.justcleanassignment.service.modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentsResponse(val results: ArrayList<Comment>) : Parcelable

@Parcelize
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) : Parcelable