package com.kokocinski.timer

import android.support.v7.app.AppCompatActivity

data class TimerUiData(
        val id: Long = 0,
        val name: String = "",
        val hours: Int = 0,
        val minutes: Int = 0,
        val command: (AppCompatActivity) -> Unit = {}
)

val TimerUiData.duration: Long get() = (hours * 1000 * 60 * 60) + (minutes * 1000 * 60L)

data class LoadTimerAction(val id: Long = 0)
data class NameChangedAction(val name: String = "")
data class HoursChangedAction(val hours: Int = 0)
data class MinutesChangedAction(val minutes: Int = 0)
class StoreTimerAction
class DeleteTimerAction