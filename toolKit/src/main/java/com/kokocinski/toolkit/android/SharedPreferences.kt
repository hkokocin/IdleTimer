package com.kokocinski.toolkit.android

import android.content.SharedPreferences

fun SharedPreferences.applyBoolean(key: String, value: Boolean) = edit().putBoolean(key, value).apply()