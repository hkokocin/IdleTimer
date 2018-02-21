package com.kokocinski.timer

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kokocinski.commonui.activityModule
import com.kokocinski.data.TimerRepository
import com.kokocinski.data.dataModule
import com.kokocinski.toolkit.coroutines.Jobs

fun timerScope(activity: AppCompatActivity) = Kodein { import(timerModule(activity), true) }

fun timerModule(activity: AppCompatActivity) = Kodein.Module {
    // Introduce some change
    import(activityModule(activity), true)
    import(dataModule(), true)

    bind<TimerView>() with provider { TimerView(instance(), instance()) }
    bind<TimerViewModel>() with provider {
        ViewModelProviders
                .of(activity, ViewModelFactory(instance()))
                .get(TimerViewModel::class.java)
    }
}

class ViewModelFactory(
        private val timerRepository: TimerRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        TimerViewModel::class.java -> TimerViewModel(timerRepository, Jobs())
        else                       -> throw IllegalArgumentException("Unknown ViewModel of type ${modelClass.name}")
    } as T

}