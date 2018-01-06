package com.kokocinski.timerlist

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kokocinski.commonui.thirdPartyUiModule
import com.kokocinski.data.dataModule

fun timerListScope(activity: AppCompatActivity) = Kodein { import(timerListModule(activity), true) }

fun timerListModule(activity: AppCompatActivity) = Kodein.Module {
    import(dataModule(), true)
    import(thirdPartyUiModule(activity), true)

    bind<TimerListView>() with provider { TimerListView(instance(), instance(), instance(), provider()) }
    bind<TimerListViewModel>() with provider { TimerListViewModel(instance(), instance(), instance(), instance()) }
    bind<TimerWidget>() with provider { TimerWidget(instance()) }
}