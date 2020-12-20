package com.mobile.justcleanassignment.posts

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.service.modal.Post
import com.mobile.justcleanassignment.service.repository.PostRepository
import com.mobile.justcleanassignment.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class PostsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: PostRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for movie list
    private val _postsMutableLiveData = MutableLiveData<Resource<MutableList<Post>>>()
    val postsMutableLiveData: LiveData<Resource<MutableList<Post>>>
        get() = _postsMutableLiveData

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            with(_postsMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setPostsData(repository.getPosts())
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

    private fun setPostsData(response: Response<ArrayList<Post>>) {
        if (response.isSuccessful) {
            _postsMutableLiveData.postValue(Resource.success(response.body()))
        } else {
            _postsMutableLiveData.postValue(Resource.error(response.errorBody().toString(), null))
        }
    }
}