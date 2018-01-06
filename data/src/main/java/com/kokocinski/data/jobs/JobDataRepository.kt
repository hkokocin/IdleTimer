package com.kokocinski.data.jobs

import android.content.res.Resources
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import com.kokocinski.data.R
import com.kokocinski.data.Timer

private const val TOLERANCE = 60000

const val CHANNEL_TIMERS = "CHANNEL_TIMERS"
const val TAG_TIMER_NOTIFICATION = "TAG_TIMER_NOTIFICATION"
const val EXTRA_TITLE = "EXTRA_TITLE"
const val EXTRA_MESSAGE = "EXTRA_MESSAGE"

class JobDataRepository(
        resources: Resources
) {
    private val messageTemplate = resources.getString(R.string.notification_message_template)

    fun start(timer: Timer) {
        val extras = PersistableBundleCompat()
        extras.putString(EXTRA_TITLE, timer.name)
        extras.putString(EXTRA_MESSAGE, messageTemplate.format(timer.name))

        JobRequest.Builder(TAG_TIMER_NOTIFICATION)
                .setExecutionWindow(timer.duration, timer.duration + TOLERANCE)
                .addExtras(extras)
                .build()
                .schedule()
    }
}