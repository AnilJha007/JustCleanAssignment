package com.mobile.justcleanassignment.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.base.BaseFragment
import com.mobile.justcleanassignment.service.utility.ApiStatus
import com.mobile.justcleanassignment.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts_tab.*

@AndroidEntryPoint
class PostsTabFragment : BaseFragment() {

    private val postsViewModel: PostsViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.fragment_posts_tab

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPagerAdapter()
        observeViewModel()
    }

    private fun observeViewModel() {
        postsViewModel.postsMutableLiveData.observe(viewLifecycleOwner, { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    constraint_parent?.snackBar("Api is loading!")
                }
                ApiStatus.SUCCESS -> {
                    res.data?.let {
                        constraint_parent?.snackBar(it.toString())
                    }
                }
                ApiStatus.ERROR -> {
                    constraint_parent?.snackBar(res.message!!)
                }
            }
        })
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