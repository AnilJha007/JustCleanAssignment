package com.mobile.justcleanassignment.posts

import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment

class FavoritePostsFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_all_posts

    override fun setPageTitle() {
        activity?.title = getString(R.string.fav_posts)
    }
}