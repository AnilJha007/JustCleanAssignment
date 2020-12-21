package com.mobile.justcleanassignment

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.mobile.justcleanassignment.service.api.ApiHelper
import com.mobile.justcleanassignment.service.modal.Post
import com.mobile.justcleanassignment.service.repository.LocalDBRepository
import com.mobile.justcleanassignment.service.repository.RemoteRepository
import com.mobile.justcleanassignment.service.utility.Resource
import com.mobile.justcleanassignment.ui.posts.PostsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var mainRepository: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalDBRepository

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var dataObserver: Observer<Resource<MutableList<Post>>>

    private lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = PostsViewModel(context, mainRepository, localRepository, networkHelper).apply {
            postsMutableLiveData.observeForever(dataObserver)
        }

    }

    @Test
    fun givenServerResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(getPostsDummyData())
                .`when`(apiHelper)
                .getPosts()
            verify(apiHelper).getPosts()
            verify(dataObserver).onChanged(Resource.success(getPostsDummyData()))
            viewModel.postsMutableLiveData.removeObserver(dataObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getPosts()
            viewModel.postsMutableLiveData.observeForever(dataObserver)
            verify(apiHelper).getPosts()
            verify(dataObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.postsMutableLiveData.removeObserver(dataObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

    private fun getPostsDummyData(): MutableList<Post>? = arrayListOf()
}