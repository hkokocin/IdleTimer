package com.kokocinski.toolkit.androidExtensions

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog

inline fun <reified T : Activity> Context.start(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

inline fun Context.alertDialog(init: AlertDialog.Builder.() -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.init()
    return builder.show()
}

val Context.notificationManager get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager