package com.kokocinski.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

private const val DEFAULT_DURATION = 60 * 60 * 1000L

@Entity
data class Timer(
        @Id var id: Long = 0,
        val name: String = "",
        val duration: Long = DEFAULT_DURATION,
        val start: Long = 0
)

@Entity
data class NotificationJob(
        @Id var id: Long = 0,
        val timerId: Long = 0,
        val jobId: Int = 0
)