package com.kokocinski.timerlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.evernote.android.job.JobManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kokocinski.commonui.thirdPartyUiModule
import com.kokocinski.data.ApplicationPreferences
import com.kokocinski.data.TimerRepository
import com.kokocinski.data.dataModule
import com.kokocinski.data.jobs.JobDataRepository
import com.kokocinski.toolkit.SystemTimerProvider
import com.kokocinski.toolkit.coroutines.Jobs

fun timerListScope(activity: AppCompatActivity) = Kodein { import(timerListModule(activity), true) }

fun timerListModule(activity: AppCompatActivity) = Kodein.Module {
    import(dataModule(), true)
    import(thirdPartyUiModule(activity), true)

    bind<TimerListView>() with provider { TimerListView(instance(), instance(), instance(), provider()) }
    bind<TimerWidget>() with provider { TimerWidget(instance()) }
    bind<TimerListViewModel>() with provider {
        ViewModelProviders
                .of(activity, ViewModelFactory(instance(), instance(), instance()))
                .get(TimerListViewModel::class.java)
    }
}

class ViewModelFactory(
        private val timerRepository: TimerRepository,
        private val jobDataRepository: JobDataRepository,
        private val preferences: ApplicationPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        TimerListViewModel::class.java -> timerViewModel()
        else                           -> throw IllegalArgumentException("Unknown ViewModel of type ${modelClass.name}")
    } as T

    private fun timerViewModel() = TimerListViewModel(
            timerRepository,
            jobDataRepository,
            Jobs(),
            SystemTimerProvider(),
            preferences

    )
}