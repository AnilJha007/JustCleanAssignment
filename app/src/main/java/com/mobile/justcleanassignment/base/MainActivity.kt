package com.mobile.justcleanassignment.base

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.mobile.justcleanassignment.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavHostComponent()
    }

    override fun getLayoutResourceId() = R.layout.activity_main

    private fun setUpNavHostComponent() {
        setupActionBarWithNavController(
            this, Navigation.findNavController(this, R.id.navHostFragment),
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}