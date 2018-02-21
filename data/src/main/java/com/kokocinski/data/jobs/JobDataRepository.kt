package com.kokocinski.data.jobs

import android.content.res.Resources
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import com.kokocinski.data.NotificationJob
import com.kokocinski.data.NotificationJob_
import com.kokocinski.data.R
import com.kokocinski.data.Timer
import io.objectbox.Box
import kotlinx.coroutines.experimental.async

private const val TOLERANCE = 60000

const val CHANNEL_TIMERS = "CHANNEL_TIMERS"
const val TAG_TIMER_NOTIFICATION = "TAG_TIMER_NOTIFICATION"
const val EXTRA_TITLE = "EXTRA_TITLE"
const val EXTRA_MESSAGE = "EXTRA_MESSAGE"

class JobDataRepository(
        resources: Resources,
        private val jobsBox: Box<NotificationJob>,
        private val jobManager: JobManager
) {
    private val messageTemplate = resources.getString(R.string.notification_message_template)

    fun start(timer: Timer) = async<Unit> {
        deleteOldJob(timer.id)

        val extras = PersistableBundleCompat()
        extras.putString(EXTRA_TITLE, timer.name)
        extras.putString(EXTRA_MESSAGE, messageTemplate.format(timer.name))

        val jobId = JobRequest.Builder(TAG_TIMER_NOTIFICATION)
                .setExecutionWindow(timer.duration, timer.duration + TOLERANCE)
                .addExtras(extras)
                .build()
                .schedule()

        jobsBox.put(NotificationJob(timerId = timer.id, jobId = jobId))
    }

    fun cancelJob(timerId: Long) = async<Unit> {
        val job = jobsBox.query()
                .equal(NotificationJob_.timerId, timerId)
                .build()
                .find()
                .firstOrNull()
                ?: return@async

        jobManager.cancel(job.jobId)
    }

    private fun deleteOldJob(timerId: Long) = async<Unit> {
        jobsBox.query()
                .equal(NotificationJob_.timerId, timerId)
                .build()
                .remove()
    }
}