package com.kokocinski.toolkit.androidExtensions

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.start(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

val Context.notificationManager get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager