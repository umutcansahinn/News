package com.umutcansahin.mynewsapp.manager.loading_indicator

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.umutcansahin.mynewsapp.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LoadingIndicatorImpl @Inject constructor(
    @ActivityContext private val context: Context
) : LoadingIndicator {

    private var loadingDialog: AlertDialog? = null

    init {
        initLoadingDialog()
    }

    private fun initLoadingDialog() {
        val builder = MaterialAlertDialogBuilder(context, R.style.StyleLoadingDialog)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_layout)
        loadingDialog = builder.create()
        loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        (context as AppCompatActivity).lifecycle.addObserver(
            DialogDismissLifecycleObserver(loadingDialog)
        )
    }

    private fun isActivityRunning() =
        (context as AppCompatActivity).lifecycle.currentState != Lifecycle.State.DESTROYED

    override fun showLoading() {
        val conditionFirst = loadingDialog == null
        val conditionSecond = isActivityRunning().not()
        if (conditionFirst or conditionSecond) return

        loadingDialog?.let { dialog ->
            if (dialog.isShowing.not()) dialog.show()
        }
    }

    override fun hideLoading() {
        val conditionFirst = loadingDialog == null
        val conditionSecond = isActivityRunning().not()
        if (conditionFirst or conditionSecond) return

        loadingDialog?.let { dialog ->
            if (dialog.isShowing) dialog.dismiss()
        }
    }
}

