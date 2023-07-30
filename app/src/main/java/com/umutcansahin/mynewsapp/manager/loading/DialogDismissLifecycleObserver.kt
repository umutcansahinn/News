package com.umutcansahin.mynewsapp.manager.loading

import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class DialogDismissLifecycleObserver(
    private var dialog: AppCompatDialog?
) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            dialog?.dismiss()
            dialog = null
        }
    }
}