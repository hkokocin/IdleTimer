package com.kokocinski.timer

data class TimerUiData(
        val id: Long = 0,
        val name: String = "",
        val hours: Int = 0,
        val minutes: Int = 0
)

data class LoadTimerEvent(val id: Long = 0)
data class NameChangedEvent(val name: String = "")
data class HoursChangedEvent(val hours: Int = 0)
data class MinutesChangedEvent(val minutes: Int = 0)