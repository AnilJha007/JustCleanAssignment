package com.mobile.justcleanassignment.utils

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, text, duration).apply { show() }
}

fun TextView.setTextOrHide(text: String?) {
    text?.let {
        setText(text)
        show()
    } ?: hide()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}