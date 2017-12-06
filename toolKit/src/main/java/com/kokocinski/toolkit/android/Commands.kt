package com.kokocinski.toolkit.android

import android.app.Activity

fun launchAppByPackage(packageName: String): (Activity) -> Unit = {
    val intent = it.packageManager.getLaunchIntentForPackage(packageName)
    it.startActivity(intent)
}
