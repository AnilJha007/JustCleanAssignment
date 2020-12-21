package com.mobile.justcleanassignment.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobile.justcleanassignment.ui.postdetails.PostDetailsFragment.Companion.IS_FAV
import com.mobile.justcleanassignment.ui.postdetails.PostDetailsFragment.Companion.POST_ID
import dagger.hilt.android.qualifiers.ApplicationContext

class FavStatusWorker(
    @ApplicationContext private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {

        val postId = workerParameters.inputData.getInt(POST_ID, 0)
        val isFav = workerParameters.inputData.getBoolean(IS_FAV, false)

        // we don't have api for fav status so just writing the log statement
        Log.v("CleanAppOfflineSync", "New favorite status for postId $postId is $isFav")

        return Result.success()
    }
}