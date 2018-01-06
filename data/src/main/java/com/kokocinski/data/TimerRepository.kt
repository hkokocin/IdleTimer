package com.kokocinski.data

import com.kokocinski.toolkit.SystemTimerProvider
import io.objectbox.Box
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class TimerRepository(
        private val timerBox: Box<Timer>,
        private val preferences: ApplicationPreferences
) {
    val all: Deferred<List<Timer>> get() = async { timerBox.all }

    fun get(id: Long): Deferred<Timer?> = async { timerBox[id] }
    fun store(timer: Timer) = async { timerBox.put(timer) }
    fun delete(id: Long): Deferred<Unit> = async { timerBox.remove(id) }

    fun initializeDefaultTimers() = async {
        if (!preferences.isDefaultTimersInitialized()) {
            timerBox.put(defaultTimers)
            preferences.setDefaultTimersInitialized()
        }
    }
}