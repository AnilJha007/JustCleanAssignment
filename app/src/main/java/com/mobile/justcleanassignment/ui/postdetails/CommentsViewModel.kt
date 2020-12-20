package com.mobile.justcleanassignment.ui.postdetails

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.repository.PostRepository
import com.mobile.justcleanassignment.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class CommentsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: PostRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isLoadedFirstTime = false

    // live data for comments list
    private val _commentsMutableLiveData = MutableLiveData<Resource<MutableList<Comment>>>()
    val commentsMutableLiveData: LiveData<Resource<MutableList<Comment>>>
        get() = _commentsMutableLiveData

    fun getComments(postId: Int) {
        viewModelScope.launch {
            with(_commentsMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setCommentsData(repository.getCommentsForPost(postId))
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
            }
        }
    }

    private fun setCommentsData(response: Response<ArrayList<Comment>>) {
        if (response.isSuccessful) {
            _commentsMutableLiveData.postValue(Resource.success(response.body()))
        } else {
            _commentsMutableLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }
}