package com.mobile.justcleanassignment.service.modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostsResponse(val results: ArrayList<Post>) : Parcelable

@Parcelize
data class Post(val userId: Int, val id: Int, val title: String, val body: String) : Parcelable