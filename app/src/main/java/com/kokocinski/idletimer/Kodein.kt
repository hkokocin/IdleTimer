package com.kokocinski.idletimer

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.kokocinski.data.dataModule
import com.kokocinski.idletimer.alarms.TimerJob
import com.kokocinski.idletimer.alarms.TimerJobCreator

fun applicationScope() = Kodein {
    import(dataModule(), true)

    bind<TimerJob>() with provider { TimerJob() }
    bind<TimerJobCreator>() with provider { TimerJobCreator(provider()) }
}