package com.kokocinski.timerlist

import android.app.Activity
import android.content.Intent
import com.kokocinski.data.Timer
import com.kokocinski.data.TimerRepository
import com.kokocinski.timer.TimerActivity
import com.kokocinski.timer.editTimer
import com.kokocinski.toolkit.SystemTimerProvider
import com.kokocinski.toolkit.android.Command
import com.kokocinski.toolkit.android.Store
import com.kokocinski.toolkit.android.launchAppByPackage
import com.kokocinski.toolkit.coroutines.Jobs
import kotlinx.coroutines.experimental.android.UI

class TimerListViewModel(
        private val timerRepository: TimerRepository,
        private val jobs: Jobs,
        private val timeProvider: SystemTimerProvider
) : Store<TimerListState>(TimerListState()) {

    init {
        loadAll()
    }

    override fun dispatch(action: Any) {
        when (action) {
            is LaunchAction      -> transient(state.copy(command = launchAppByPackage(action.packageName)))
            is DeleteTimerAction -> delete(action.id)
            is EditTimerAction   -> transient(state.copy(command = editTimer(action.id)))
            is StartTimerAction  -> onStartTimer(action.timer)
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.clear()
    }

    private fun onStartTimer(timer: Timer) {
        val timers = state.timers
                .map {
                    if (it.id == timer.id) startTimer(it, timer)
                    else it
                }
                .sortedBy { it.timer.millisRemaining }

        update(state.copy(timers = timers))
    }

    private fun startTimer(
            widgetState: TimerWidgetState,
            expiredTimer: Timer
    ): TimerWidgetState {
        val timer = expiredTimer.copy(start = timeProvider.currentTimeMillis())
        timerRepository.store(timer)
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
                .map { it.toWidgetState(::dispatch) }
                .sortedBy { it.timer.millisRemaining }

        update(state.copy(timers = timers))
    }
}

private class EditTimerCommand(private val id: Long) : Command<Activity> {
    override fun invoke(context: Activity) {
        context.startActivity(Intent(context, TimerActivity::class.java))
    }
}