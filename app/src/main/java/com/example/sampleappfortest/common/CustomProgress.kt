package com.example.sampleappfortest.common

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.sampleappfortest.R

class CustomProgress : Activity {
    private var alertDialog: AlertDialog? = null
    private var dialogBuilder: AlertDialog.Builder
    var mContext: Context

    constructor(context: Context, showMessage: String) {
        mContext = context
        dialogBuilder = AlertDialog.Builder(mContext)//, android.R.style.Theme_Panel)
        val inflater = LayoutInflater.from(mContext)
        val dialogView = inflater.inflate(R.layout.layout_progress_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        alertDialog = dialogBuilder.create()
    }

    constructor(context: Context) {
        mContext = context
        dialogBuilder = AlertDialog.Builder(mContext)//, android.R.style.Theme_Panel)
        val inflater = LayoutInflater.from(mContext)
        val dialogView = inflater.inflate(R.layout.layout_progress_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        alertDialog = dialogBuilder.create()
    }

    fun showProgressDialog() {
        alertDialog?.let {
            if (!(mContext as Activity).isFinishing && !(it.isShowing)) {
                it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.show()
            }
        }
    }

    fun hideProgressDialog() {
        alertDialog?.let {
            if (!(mContext as Activity).isFinishing && it.isShowing) {
                alertDialog?.dismiss()
            }
        }
    }
}
