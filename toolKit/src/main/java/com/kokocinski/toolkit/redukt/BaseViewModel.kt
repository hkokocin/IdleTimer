package com.kokocinski.toolkit.redukt

import android.arch.lifecycle.ViewModel


abstract class BaseViewModel<T : Any>(initialState: T) : ViewModel(), Store<T> {

    override val subscriptions = mutableListOf<(T) -> Unit>()

    @Transient override var state: T = initialState

    private val shards = mutableSetOf<StoreShard<*>>()

    override fun dispatch(action: Any) {
        shards.forEach { it.dispatch(action) }
    }

    protected fun <S : Any> bindShard(shard: StoreShard<S>, newState: (S) -> T) {
        shards.add(shard)

        shard.bind(
                {
                    transient(newState(it))
                    shard.notifyStateChanged(it)
                },
                {
                    update(newState(it))
                    shard.notifyStateChanged(it)
                },
                {
                    silentUpdate(newState(it))
                }
        )
    }

    protected fun transient(value: T) = notifyStateChanged(value)

    protected fun update(value: T) {
        state = value
        notifyStateChanged(state)
    }

    protected fun silentUpdate(value: T) {
        state = value
    }
}
