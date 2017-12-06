package com.kokocinski.timerlist

import android.app.Activity
import com.kokocinski.data.Timer

data class TimerListState(
        val timers: List<TimerWidgetState> = emptyList(),
        val command: (Activity) -> Unit = {}
)

data class TimerWidgetState(
        val timer: Timer = Timer(),
        val timeRemaining: String = "00:00:00",
        val timerFinished: Boolean = false,
        val dispatch: (Any) -> Unit = {}
){
    val id: Long get() = timer.id
    val name: String get() = timer.name
}

data class LaunchAction(val packageName: String = "")
data class EditTimerAction(val id: Long = 0)
data class DeleteTimerAction(val id: Long = 0)
data class StartTimerAction(val timer: Timer = Timer())