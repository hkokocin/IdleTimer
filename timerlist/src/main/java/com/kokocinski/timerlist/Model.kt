package com.kokocinski.timerlist

import android.support.v7.app.AppCompatActivity
import com.kokocinski.data.Timer

data class TimerListState(
        val timers: List<TimerWidgetState> = emptyList(),
        val command: (AppCompatActivity) -> Unit = {}
)

data class TimerWidgetState(
        val timer: Timer = Timer(),
        val dispatch: (Any) -> Unit = {}
) {
    val id: Long get() = timer.id
    val name: String get() = timer.name
    val start: Long get() = timer.start
    val duration: Long get() = timer.duration
}

data class LaunchAction(val packageName: String = "")
data class EditTimerAction(val id: Long = 0)
data class DeleteTimerAction(val id: Long = 0)
data class StartTimerAction(val timer: Timer = Timer())
data class RestartTimerAction(val timer: Timer = Timer())
class UpdateTimersAction
class LoadDefaultTimersAction
class RejectDefaultTimersAction