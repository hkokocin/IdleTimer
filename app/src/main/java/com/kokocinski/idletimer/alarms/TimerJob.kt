package com.kokocinski.idletimer.alarms

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.Job
import com.kokocinski.data.jobs.CHANNEL_TIMERS
import com.kokocinski.data.jobs.EXTRA_MESSAGE
import com.kokocinski.data.jobs.EXTRA_TITLE
import com.kokocinski.idletimer.R
import com.kokocinski.timerlist.TimerListActivity
import com.kokocinski.toolkit.androidExtensions.notificationManager


class TimerJob : Job() {

    override fun onRunJob(params: Params): Result {
        val title = params.extras.getString(EXTRA_TITLE, "")
        val message = params.extras.getString(EXTRA_MESSAGE, "")

        val notification = NotificationCompat.Builder(context, CHANNEL_TIMERS)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(createResultIntent())
                .setAutoCancel(true)
                .build()

        context.notificationManager.notify(title.hashCode(), notification)

        return Result.SUCCESS
    }

    private fun createResultIntent(): PendingIntent? {
        val resultIntent = Intent(context, TimerListActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(TimerListActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)

        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        return resultPendingIntent
    }
}