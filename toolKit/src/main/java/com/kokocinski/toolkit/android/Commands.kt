package com.kokocinski.toolkit.android

import android.app.Activity

fun launchAppByPackage(packageName: String): (Activity) -> Unit = {
    val intent = it.packageManager.getLaunchIntentForPackage(packageName)
    it.startActivity(intent)
}

fun finishActivity(resultCode: Int = Activity.RESULT_OK): (Activity) -> Unit = {
    it.setResult(resultCode)
    it.finish()
}