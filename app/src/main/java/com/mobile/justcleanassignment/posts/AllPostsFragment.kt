package com.mobile.justcleanassignment.posts

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment
import com.mobile.justcleanassignment.service.modal.Post
import com.mobile.justcleanassignment.service.utility.ApiStatus
import com.mobile.justcleanassignment.utils.Util.getAlertDialog
import com.mobile.justcleanassignment.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_posts.*

@AndroidEntryPoint
class AllPostsFragment : BaseFragment() {

    private val postsViewModel: PostsViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var spotsDialog: AlertDialog

    override fun getLayoutResourceId() = R.layout.fragment_all_posts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        observeViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())
        postsAdapter = PostsAdapter(::handleItemClick)
        recycler_view_all_posts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = postsAdapter
        }
    }

    private fun handleItemClick(postId: Int) {
    }

    private fun observeViewModel() {
        postsViewModel.postsMutableLiveData.observe(viewLifecycleOwner, { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized)
                        spotsDialog.show()
                }
                ApiStatus.SUCCESS -> {
                    if (::spotsDialog.isInitialized && spotsDialog.isShowing)
                        spotsDialog.hide()

                    res.data?.let {
                        setData(it)
                    }
                }
                ApiStatus.ERROR -> {
                    if (::spotsDialog.isInitialized && spotsDialog.isShowing)
                        spotsDialog.hide()
                    constraint_all_posts?.snackBar(res.message!!)
                }
            }
        })
    }

    private fun setData(posts: MutableList<Post>) {
        postsAdapter.updateData(posts)
    }
}