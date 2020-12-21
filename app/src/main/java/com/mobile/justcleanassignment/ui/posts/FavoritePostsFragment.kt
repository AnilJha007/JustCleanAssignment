package com.mobile.justcleanassignment.ui.posts

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment
import com.mobile.justcleanassignment.service.modal.Post
import com.mobile.justcleanassignment.ui.postdetails.PostDetailsFragment
import com.mobile.justcleanassignment.utils.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_posts.*

@AndroidEntryPoint
class FavoritePostsFragment : BaseFragment() {

    private val postsViewModel: PostsViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var spotsDialog: AlertDialog

    override fun getLayoutResourceId() = R.layout.fragment_favorite_posts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        observeViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = Util.getAlertDialog(requireContext())
        postsAdapter = PostsAdapter(::handleItemClick)
        recycler_view_fav_posts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = postsAdapter
        }
    }

    private fun handleItemClick(post: Post) {
        findNavController().navigate(R.id.postDetailsDest, Bundle().apply {
            putParcelable(PostDetailsFragment.ARGS_POST, post)
        })
    }

    private fun observeViewModel() {
        postsViewModel.getAllFavoritesPosts().observe(viewLifecycleOwner, {
            setData(it)
        })
    }

    private fun setData(posts: MutableList<Post>) {
        postsAdapter.updateData(posts)
    }
}