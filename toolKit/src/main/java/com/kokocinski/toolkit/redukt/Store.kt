package com.kokocinski.toolkit.redukt

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

interface Store<T> {

    val subscriptions: MutableList<(T) -> Unit>

    var state: T

    fun dispatch(action: Any)

    /**
     * Add an observer that has to be cleaned up manually. You can do so by invoking the returned function.
     */
    fun observeForever(observer: (T) -> Unit): () -> Unit {
        subscriptions.add(observer)
        return { subscriptions.remove(observer) }
    }

    /**
     * Add an observer that is automatically cleaned up if the lifecycle pushes an onDestroy
     */
    fun observe(lifecycle: Lifecycle, observer: (T) -> Unit) {
        lifecycle.addObserver(LifecycleAwareSubscription(observer, subscriptions))
        subscriptions.add(observer)
        observer(state)
    }

    fun notifyStateChanged(state: T) = subscriptions.forEach { it(state) }
}

private class LifecycleAwareSubscription<T>(
        private val observer: (T) -> Unit,
        private val subscriptions: MutableList<(T) -> Unit>
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        subscriptions.remove(observer)
    }
}