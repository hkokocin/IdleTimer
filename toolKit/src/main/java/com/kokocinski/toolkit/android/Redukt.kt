package com.kokocinski.toolkit.android

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel

interface Command<in T> {
    fun invoke(context: T)
}

class NoCommand<in T>() : Command<T> {
    override fun invoke(context: T) {}
}

abstract class Store<T : Any>(initialState: T) : ViewModel() {

    val subscriptions = mutableListOf<(T) -> Unit>()

    var state: T = initialState
        private set

    abstract fun dispatch(action: Any)

    fun transient(value: T) = notifyStateChanged(value)

    fun update(value: T) {
        state = value
        notifyStateChanged(state)
    }

    fun silentUpdate(value: T) {
        state = value
    }

    fun observeForever(observer: (T) -> Unit): () -> Unit {
        subscriptions.add(observer)
        return { subscriptions.remove(observer) }
    }

    fun observe(lifecycle: Lifecycle, observer: (T) -> Unit) {
        lifecycle.addObserver(LifecycleAwareSubscription(observer))
        subscriptions.add(observer)
        observer(state)
    }

    protected fun notifyStateChanged(state: T) = subscriptions.forEach { it(state) }

    internal inner class LifecycleAwareSubscription(
            private val subscription: (T) -> Unit
    ) : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun unsubscribe() {
            subscriptions.remove(subscription)
        }
    }
}
