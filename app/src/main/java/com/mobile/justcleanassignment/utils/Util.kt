package com.mobile.justcleanassignment.utils

import android.app.AlertDialog
import android.content.Context
import com.mobile.justcleanassignment.R
import dmax.dialog.SpotsDialog

object Util {
    fun getAlertDialog(context: Context, cancelable: Boolean = false): AlertDialog {
        return SpotsDialog.Builder()
            .setContext(context)
            .setTheme(R.style.DialogTheme)
            .setCancelable(cancelable)
            .build()
    }
}