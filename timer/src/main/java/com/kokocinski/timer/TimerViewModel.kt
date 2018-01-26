package com.kokocinski.timer

import com.kokocinski.data.Timer
import com.kokocinski.data.TimerRepository
import com.kokocinski.toolkit.coroutines.Jobs
import com.kokocinski.toolkit.redukt.BaseViewModel
import kotlinx.coroutines.experimental.android.UI

class TimerViewModel(
        private val timerRepository: TimerRepository,
        private val jobs: Jobs
) : BaseViewModel<TimerUiData>(TimerUiData()) {

    override fun dispatch(action: Any) {
        when (action) {
            is LoadTimerEvent    -> loadTimer(action.id)
            is NameChangedEvent    -> silentUpdate(state.copy(name = action.name))
            is HoursChangedEvent   -> silentUpdate(state.copy(hours = action.hours))
            is MinutesChangedEvent -> silentUpdate(state.copy(minutes = action.minutes))
        }
    }

    private fun loadTimer(id: Long) = jobs.launch(UI) {
        timerRepository.get(id).await()?.apply { update(toUiData()) }
    }

    private fun Timer.toUiData() = TimerUiData(
            id,
            name,
            (duration / (1000 * 60 * 60)).toInt(),
            (duration / (1000 * 60) % 60).toInt()
    )
}