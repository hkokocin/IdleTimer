package com.kokocinski.idletimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import com.evernote.android.job.JobManager
import com.github.salomonbrys.kodein.instance
import com.kokocinski.data.TimerRepository
import com.kokocinski.data.initDataModule
import com.kokocinski.data.jobs.CHANNEL_TIMERS
import com.kokocinski.idletimer.alarms.TimerJobCreator
import com.kokocinski.toolkit.androidExtensions.notificationManager
import com.squareup.leakcanary.LeakCanary


class IdleTimerApp : Application() {

    private val injector by lazy { applicationScope() }
    private val timerRepository by lazy { injector.instance<TimerRepository>() }
    private val timerJobCreator by lazy { injector.instance<TimerJobCreator>() }
    private val jobManager by lazy { injector.instance<JobManager>() }

    override fun onCreate() {
        super.onCreate()

        initDataModule(this)

        timerRepository.initializeDefaultTimers()
        jobManager.addJobCreator(timerJobCreator)

        if (!LeakCanary.isInAnalyzerProcess(this)) LeakCanary.install(this)

        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT < 26) return

        val channel = NotificationChannel(
                CHANNEL_TIMERS,
                getString(R.string.timer_channel_name),
                NotificationManager.IMPORTANCE_HIGH
        )

        channel.description = getString(R.string.timer_channel_description)
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(300, 50, 50, 50)

        notificationManager.createNotificationChannel(channel)
    }
}