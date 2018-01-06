package com.kokocinski.idletimer.alarms

import com.evernote.android.job.JobCreator
import com.kokocinski.data.jobs.TAG_TIMER_NOTIFICATION

class TimerJobCreator(
        private val timerJobProvider: () -> TimerJob
) : JobCreator {

    override fun create(tag: String) = when (tag) {
        TAG_TIMER_NOTIFICATION -> timerJobProvider()
        else                   -> throw IllegalArgumentException("Unknown tag '$tag'")
    }
}