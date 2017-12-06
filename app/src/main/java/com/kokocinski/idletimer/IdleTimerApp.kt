package com.kokocinski.idletimer

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.kokocinski.data.TimerRepository
import com.kokocinski.data.dataModule
import com.kokocinski.data.initDataModule


class IdleTimerApp : Application() {

    private val injector by lazy { Kodein { import(dataModule(), true) } }
    private val timerRepository by lazy { injector.instance<TimerRepository>() }

    override fun onCreate() {
        super.onCreate()
        initDataModule(this)
        timerRepository.initializeDefaultTimers()
    }
}