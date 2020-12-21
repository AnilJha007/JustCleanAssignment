package com.mobile.justcleanassignment.ui.postdetails

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.repository.LocalDBRepository
import com.mobile.justcleanassignment.service.repository.RemoteRepository
import com.mobile.justcleanassignment.service.utility.Resource
import com.mobile.justcleanassignment.ui.postdetails.PostDetailsFragment.Companion.IS_FAV
import com.mobile.justcleanassignment.ui.postdetails.PostDetailsFragment.Companion.POST_ID
import com.mobile.justcleanassignment.utils.FavStatusWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class PostDetailsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val remoteRepository: RemoteRepository,
    private val localDBRepository: LocalDBRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isLoadedFirstTime = false

    // live data for comments list
    private val _commentsMediatorLiveData = MediatorLiveData<Resource<MutableList<Comment>>>()
    val commentsMutableLiveData: LiveData<Resource<MutableList<Comment>>>
        get() = _commentsMediatorLiveData

    fun getComments(postId: Int) {
        viewModelScope.launch {
            with(_commentsMediatorLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setCommentsData(remoteRepository.getCommentsForPost(postId))
                    } catch (e: Exception) {
                        postValue(
                            Resource.error(context.getString(R.string.something_went_wrong), null)
                        )
                    }
                } else {
                    postValue(
                        Resource.error(context.getString(R.string.no_internet_error), null)
                    )
                }
                addSource(localDBRepository.getCommentsFroPost(postId)) { comments ->
                    postValue(Resource.success(comments))
                }
            }
        }
    }

    private fun setCommentsData(response: Response<ArrayList<Comment>>) {
        if (response.isSuccessful) {
            viewModelScope.launch {
                response.body()?.let {
                    localDBRepository.insertAllComments(it)
                }
            }
        } else {
            _commentsMediatorLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }

    fun updatePostFavStatus(postId: Int, isFav: Boolean) {
        viewModelScope.launch {
            val result = localDBRepository.updatePost(postId, isFav)
            // We don't have internet so we need to schedule a job that will update fav status to server later end
            if (result == 1 && !networkHelper.isNetworkConnected()) {
                updateFavStatus(postId, isFav)
            }
        }
    }

    private fun updateFavStatus(postId: Int, isFav: Boolean) {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData = workDataOf(POST_ID to postId, IS_FAV to isFav)
        val request: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<FavStatusWorker>().setConstraints(constraint)
                .setInputData(inputData).build()

        WorkManager.getInstance(context).enqueue(request)
    }
}