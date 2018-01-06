package com.kokocinski.idletimer

import com.github.salomonbrys.kodein.*
import com.kokocinski.data.dataModule
import com.kokocinski.idletimer.alarms.TimerJob
import com.kokocinski.idletimer.alarms.TimerJobCreator

fun applicationScope() = Kodein {
    import(dataModule(), true)

    bind<TimerJobCreator>() with provider { TimerJobCreator(provider()) }
    bind<TimerJob>() with provider { TimerJob() }
}