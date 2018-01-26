package com.kokocinski.timer

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kokocinski.data.dataModule

fun timerScope(activity: AppCompatActivity) = Kodein { import(timerModule(activity), true) }

fun timerModule(activity: AppCompatActivity) = Kodein.Module {
    import(dataModule(), true)

    bind<TimerView>() with provider { TimerView(instance()) }
    bind<TimerViewModel>() with provider { TimerViewModel(instance(), instance()) }
}