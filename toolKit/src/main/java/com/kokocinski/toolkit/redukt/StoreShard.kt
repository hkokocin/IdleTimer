package com.kokocinski.toolkit.redukt

abstract class StoreShard<T : Any>(initialState: T) : Store<T> {

    override val subscriptions = mutableListOf<(T) -> Unit>()

    protected lateinit var transient: (T) -> Any
    protected lateinit var update: (T) -> Any
    protected lateinit var silentUpdate: (T) -> Any

    override var state: T = initialState

    fun bind(
            transient: (T) -> Any,
            update: (T) -> Any,
            silentUpdate: (T) -> Any
    ) {
        this.transient = transient

        this.update = {
            state = it
            update(it)
        }

        this.silentUpdate = {
            state = it
            silentUpdate(it)
        }
    }
}
