package com.kokocinski.timerlist

import com.kokocinski.data.ApplicationPreferences
import com.kokocinski.data.Timer
import com.kokocinski.data.TimerRepository
import com.kokocinski.data.jobs.JobDataRepository
import com.kokocinski.timer.editTimer
import com.kokocinski.toolkit.SystemTimerProvider
import com.kokocinski.toolkit.android.launchAppByPackage
import com.kokocinski.toolkit.coroutines.Jobs
import com.kokocinski.toolkit.redukt.BaseViewModel
import kotlinx.coroutines.experimental.android.UI

class TimerListViewModel(
        private val timerRepository: TimerRepository,
        private val jobDataRepository: JobDataRepository,
        private val jobs: Jobs,
        private val timeProvider: SystemTimerProvider,
        private val preferences: ApplicationPreferences
) : BaseViewModel<TimerListState>(TimerListState()) {

    init {
        loadAll()
    }

    override fun dispatch(action: Any) {
        when (action) {
            is LaunchAction            -> transient(state.copy(command = launchAppByPackage(action.packageName)))
            is DeleteTimerAction       -> delete(action.id)
            is EditTimerAction         -> transient(state.copy(command = editTimer(action.id)))
            is StartTimerAction        -> onStartTimer(action.timer)
            is RestartTimerAction      -> restartTimer(action.timer)
            is UpdateTimersAction      -> loadAll()
            is LoadDefaultTimersAction -> loadDefaultTimers()
            is RejectDefaultTimersAction -> preferences.isDefaultTimersInitialized()
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.clear()
    }

    private fun onStartTimer(timer: Timer) = jobs.launch(UI) {
        val timers = state.timers
                .map { if (it.id == timer.id) startTimer(it, timer) else it }
                .sortedBy { it.timer.millisRemaining }

        update(state.copy(timers = timers))
        transient(state.copy(command = clearNotification(timer.name.hashCode())))
    }

    private fun restartTimer(timer: Timer) = jobs.launch(UI) {
        jobDataRepository.cancelJob(timer.id)
        onStartTimer(timer)
    }

    private suspend fun startTimer(
            widgetState: TimerWidgetState,
            expiredTimer: Timer
    ): TimerWidgetState {
        val timer = expiredTimer.copy(start = timeProvider.currentTimeMillis())
        timerRepository.store(timer)
        jobDataRepository.start(timer).await()
        return widgetState.copy(timer = timer)
    }

    private fun delete(id: Long) {
        timerRepository.delete(id)
        val timers = state.timers.filterNot { it.id == id }
        update(state.copy(timers = timers))
    }

    private fun loadAll() = jobs.launch(UI) {
        val timers = timerRepository.all
                .await()
                .toWidgetStates()

        if (timers.isEmpty() && !preferences.isDefaultTimersInitialized())
            transient(state.copy(command = askToInitializeDefaultTimers(::dispatch)))
        else
            update(state.copy(timers = timers))
    }

    private fun loadDefaultTimers() = jobs.launch(UI) {
        val timers = timerRepository.initializeDefaultTimers()
                .await()
                .toWidgetStates()

        update(state.copy(timers = timers))
    }

    private fun List<Timer>.toWidgetStates() = map { it.toWidgetState(::dispatch) }
            .sortedBy { it.timer.millisRemaining }
}
