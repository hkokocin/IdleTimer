package com.kokocinski.timer

import com.kokocinski.data.Timer
import com.kokocinski.data.TimerRepository
import com.kokocinski.toolkit.android.finishActivity
import com.kokocinski.toolkit.coroutines.Jobs
import com.kokocinski.toolkit.redukt.BaseViewModel
import kotlinx.coroutines.experimental.android.UI

class TimerViewModel(
        private val timerRepository: TimerRepository,
        private val jobs: Jobs
) : BaseViewModel<TimerUiData>(TimerUiData()) {

    private var timer = Timer()

    override fun dispatch(action: Any) {
        when (action) {
            is LoadTimerAction      -> loadTimer(action.id)
            is NameChangedAction    -> silentUpdate(state.copy(name = action.name))
            is HoursChangedAction   -> silentUpdate(state.copy(hours = action.hours))
            is MinutesChangedAction -> silentUpdate(state.copy(minutes = action.minutes))
            is DeleteTimerAction    -> deleteTimer()
            is StoreTimerAction     -> onStore()
        }
    }

    private fun loadTimer(id: Long) = jobs.launch(UI) {
        if (id != 0L)
            timer = timerRepository.get(id).await() ?: Timer()

        update(timer.toUiData())
    }

    private fun Timer.toUiData() = TimerUiData(
            id,
            name,
            (duration / (1000 * 60 * 60)).toInt(),
            (duration / (1000 * 60) % 60).toInt()
    )

    private fun onStore() = jobs.launch(UI) {
        storeTimer()
        finish()
    }

    private suspend fun storeTimer() {

        if (state.name.isBlank() || state.duration == 0L) return

        val updatedTimer = timer.copy(
                name = state.name,
                duration = state.duration
        )

        timerRepository.store(updatedTimer).await()
    }

    private fun deleteTimer() = jobs.launch(UI) {
        if (state.id != 0L)
            timerRepository.delete(state.id).await()

        finish()
    }

    private fun finish() = transient(state.copy(command = finishActivity()))

}