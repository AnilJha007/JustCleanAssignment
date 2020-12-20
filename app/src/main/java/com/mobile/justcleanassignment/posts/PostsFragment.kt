package com.mobile.justcleanassignment.posts

import android.os.Bundle
import android.view.View
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment

class PostsFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_posts

    override fun setPageTitle() {
        activity?.title = getString(R.string.posts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceFragment()
    }

    private fun replaceFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container_view, PostsTabFragment(), javaClass.simpleName).commit()
        childFragmentManager.executePendingTransactions()
    }
}