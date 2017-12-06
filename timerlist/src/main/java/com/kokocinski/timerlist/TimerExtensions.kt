package com.kokocinski.timerlist

import com.kokocinski.data.Timer
import java.text.SimpleDateFormat
import java.util.*

fun Timer.toWidgetState(dispatch: (Any) -> Unit): TimerWidgetState = TimerWidgetState(
        this,
        getTimerString(),
        isFinished,
        dispatch
)

private val formatter by lazy { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

private val Timer.isFinished: Boolean get() = System.currentTimeMillis() - start > duration

private fun Timer.getTimerString(): String {
    val delta = start + duration - System.currentTimeMillis()
    val time = maxOf(delta, 0)
    return formatter.format(time)
}