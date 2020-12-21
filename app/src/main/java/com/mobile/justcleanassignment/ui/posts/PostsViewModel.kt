package com.mobile.justcleanassignment.ui.posts

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.service.modal.Post
import com.mobile.justcleanassignment.service.repository.LocalDBRepository
import com.mobile.justcleanassignment.service.repository.RemoteRepository
import com.mobile.justcleanassignment.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class PostsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val remoteRepository: RemoteRepository,
    private val localDBRepository: LocalDBRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isLoadedFirstTime = false

    // live data for post list
    private val _postsMediatorLiveData = MediatorLiveData<Resource<MutableList<Post>>>()
    val postsMutableLiveData: LiveData<Resource<MutableList<Post>>>
        get() = _postsMediatorLiveData

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            with(_postsMediatorLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setPostsData(remoteRepository.getPosts())
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
                addSource(localDBRepository.getPosts()) { posts ->
                    postValue(Resource.success(posts))
                }
            }
        }
    }

    private fun setPostsData(response: Response<ArrayList<Post>>) {
        if (response.isSuccessful) {
            _postsMediatorLiveData.postValue(Resource.success(response.body()))
            viewModelScope.launch {
                response.body()?.let {
                    localDBRepository.insertAllPosts(it)
                }
            }
        } else {
            _postsMediatorLiveData.postValue(Resource.error(response.errorBody().toString(), null))
        }
    }
}