package com.kokocinski.timerlist

import android.support.v7.app.AppCompatActivity
import com.kokocinski.toolkit.androidExtensions.alertDialog
import com.kokocinski.toolkit.androidExtensions.notificationManager

fun clearNotification(id: Int): (AppCompatActivity) -> Unit = {
    it.notificationManager.cancel(id)
}

fun askToInitializeDefaultTimers(dispatch: (Any) -> Unit): (AppCompatActivity) -> Unit = {
    it.alertDialog {
        setTitle(R.string.templates_dialog_title)
        setMessage(R.string.templates_dialog_message)
        setPositiveButton(R.string.apply_template) { _, _ -> dispatch(LoadDefaultTimersAction()) }
        setNegativeButton(R.string.no) { _, _ -> dispatch(RejectDefaultTimersAction()) }
    }
}