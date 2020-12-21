package com.mobile.justcleanassignment

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bharatagri.mobile.service.utility.NetworkHelper
import com.mobile.justcleanassignment.service.api.ApiHelper
import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.repository.LocalDBRepository
import com.mobile.justcleanassignment.service.repository.RemoteRepository
import com.mobile.justcleanassignment.service.utility.Resource
import com.mobile.justcleanassignment.ui.postdetails.PostDetailsViewModel
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
class PostDetailsViewModelTest {

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
    private lateinit var dataObserver: Observer<Resource<MutableList<Comment>>>

    private lateinit var viewModel: PostDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel =
            PostDetailsViewModel(context, mainRepository, localRepository, networkHelper).apply {
                commentsMutableLiveData.observeForever(dataObserver)
            }

    }

    @Test
    fun givenServerResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val postId = 1
            doReturn(getCommentsDummyData())
                .`when`(apiHelper)
                .getCommentsForPost(postId)
            verify(apiHelper).getCommentsForPost(postId)
            verify(dataObserver).onChanged(Resource.success(getCommentsDummyData()))
            viewModel.commentsMutableLiveData.removeObserver(dataObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val postId = 5
            val errorMessage = "Error Message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getCommentsForPost(postId)
            viewModel.commentsMutableLiveData.observeForever(dataObserver)
            verify(apiHelper).getCommentsForPost(postId)
            verify(dataObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.commentsMutableLiveData.removeObserver(dataObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

    private fun getCommentsDummyData(): MutableList<Comment> {
        val comments = arrayListOf<Comment>()
        comments.add(
            Comment(
                1,
                1,
                "Test comment 1",
                "test1@test.com",
                "test1 test1 test1 test1 test1"
            )
        )
        comments.add(
            Comment(
                1,
                2,
                "Test comment 2",
                "test2@test.com",
                "test2 test2 test2 test2 test2"
            )
        )
        comments.add(
            Comment(
                2,
                3,
                "Test comment 3",
                "test3@test.com",
                "test3 test3 test3 test3 test3"
            )
        )
        return comments
    }
}