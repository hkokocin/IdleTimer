package com.kokocinski.timerlist

import android.support.v7.app.AppCompatActivity
import com.kokocinski.toolkit.androidExtensions.notificationManager

fun clearNotification(id: Int): (AppCompatActivity) -> Unit = {
    it.notificationManager.cancel(id)
}