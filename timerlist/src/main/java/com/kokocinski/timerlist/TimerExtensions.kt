package com.kokocinski.timerlist

import android.annotation.SuppressLint
import com.kokocinski.data.Timer
import java.text.SimpleDateFormat
import java.util.*


fun Timer.toWidgetState(dispatch: (Any) -> Unit): TimerWidgetState = TimerWidgetState(
        this,
        dispatch
)

val Timer.isFinished: Boolean get() = System.currentTimeMillis() - start > duration

fun Timer.getTimeRemainingString(): String {
    val time = maxOf(millisRemaining, 0)

    val segments = listOf(
            time / (1000 * 60 * 60) % 24,
            time / (1000 * 60) % 60,
            time / 1000 % 60
    )

    return segments
            .map { it.toString() }
            .map { if (it.length == 1) "0" + it else it }
            .joinToString(":")
}

val Timer.millisRemaining get() = start + duration - System.currentTimeMillis()