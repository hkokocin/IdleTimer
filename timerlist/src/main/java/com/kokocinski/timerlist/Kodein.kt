package com.kokocinski.timerlist

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kokocinski.commonui.activityModule
import com.kokocinski.data.dataModule
import com.kokocinski.toolkit.toolKitModule

fun timerListScope(activity: AppCompatActivity) = Kodein { import(timerListModule(activity), true) }

fun timerListModule(activity: AppCompatActivity) = Kodein.Module {
    import(activityModule(activity), true)
    import(dataModule(), true)
    import(toolKitModule(), true)

    bind<TimerListView>() with provider { TimerListView(instance(), instance(), instance(), provider()) }
    bind<TimerListViewModel>() with provider { TimerListViewModel(instance(), instance(), instance()) }
    bind<TimerWidget>() with provider { TimerWidget() }
}