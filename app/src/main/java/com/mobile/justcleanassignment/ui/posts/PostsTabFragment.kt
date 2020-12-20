package com.mobile.justcleanassignment.ui.posts

import android.os.Bundle
import android.view.View
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts_tab.*

@AndroidEntryPoint
class PostsTabFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_posts_tab

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPagerAdapter()
    }

    private fun setUpPagerAdapter() {
        activity?.let {
            val adapter = ViewPagerAdapter(it.supportFragmentManager).apply {
                addFragment(AllPostsFragment(), getString(R.string.all_posts))
                addFragment(FavoritePostsFragment(), getString(R.string.fav_posts))
            }
            view_pager.adapter = adapter
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}