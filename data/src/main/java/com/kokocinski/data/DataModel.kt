package com.kokocinski.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Timer(
        @Id var id: Long = 0,
        val name: String = "",
        val duration: Long = 0,
        val start: Long = 0
)

@Entity
data class NotificationJob(
        @Id var id: Long = 0,
        val timerId: Long = 0,
        val jobId: Int = 0
)