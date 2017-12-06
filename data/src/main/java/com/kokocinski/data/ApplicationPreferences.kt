package com.kokocinski.data

import android.content.SharedPreferences
import com.kokocinski.toolkit.android.applyBoolean

private const val DEFAULT_TIMERS_INITIALIZED = "DEFAULT_TIMERS_INITIALIZED"

class ApplicationPreferences(
        private val preferences: SharedPreferences
) {
    fun isDefaultTimersInitialized() = preferences.getBoolean(DEFAULT_TIMERS_INITIALIZED, false)
    fun setDefaultTimersInitialized() = preferences.applyBoolean(DEFAULT_TIMERS_INITIALIZED, true)
}