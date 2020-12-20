package com.mobile.justcleanassignment.ui.postdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment
import com.mobile.justcleanassignment.service.modal.Comment
import com.mobile.justcleanassignment.service.modal.Post
import com.mobile.justcleanassignment.service.utility.ApiStatus
import com.mobile.justcleanassignment.utils.Util
import com.mobile.justcleanassignment.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_posts.*
import kotlinx.android.synthetic.main.fragment_post_details.*
import kotlinx.android.synthetic.main.item_post.*

@AndroidEntryPoint
class PostDetailsFragment : BaseFragment() {

    private var post: Post? = null
    private lateinit var spotsDialog: AlertDialog
    private lateinit var commentsAdapter: CommentsAdapter
    private val commentsViewModel: CommentsViewModel by viewModels()

    companion object {
        const val ARGS_POST = "post"
    }

    override fun setPageTitle() {
        activity?.title = getString(R.string.post_details)
    }

    override fun getLayoutResourceId() = R.layout.fragment_post_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialStateData()
        observeViewModel()
    }

    private fun setInitialStateData() {
        post = arguments?.getParcelable(ARGS_POST)
        post?.run {
            tv_title.text = title
            tv_body.text = body
        }
        spotsDialog = Util.getAlertDialog(requireContext())
        commentsAdapter = CommentsAdapter()
        recycler_view_comments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = commentsAdapter
        }
    }

    private fun observeViewModel() {
        commentsViewModel.commentsMutableLiveData.observe(viewLifecycleOwner, { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized && !commentsViewModel.isLoadedFirstTime) {
                        commentsViewModel.isLoadedFirstTime = true
                        spotsDialog.show()
                    }
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
        post?.let { commentsViewModel.getComments(it.id) }
    }

    private fun setData(comments: MutableList<Comment>) {
        commentsAdapter.updateData(comments)
    }
}