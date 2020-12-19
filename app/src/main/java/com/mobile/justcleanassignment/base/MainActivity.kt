package com.mobile.justcleanassignment.base

import android.os.Bundle
import com.mobile.justcleanassignment.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getLayoutResourceId() = R.layout.activity_main
}