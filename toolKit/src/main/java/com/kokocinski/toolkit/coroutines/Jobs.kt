package com.kokocinski.toolkit.coroutines

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

class Jobs {
    private val jobs = mutableListOf<Job>()

    fun launch(
            context: CoroutineContext = DefaultDispatcher,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            errorHandler: (Throwable) -> Unit = {},
            block: suspend CoroutineScope.() -> Unit
    ): Job {
        val job = kotlinx.coroutines.experimental.launch(context, start, block)
        jobs.add(job)
        job.invokeOnCompletion {
            jobs.remove(job)
            it?.printStackTrace() ?: return@invokeOnCompletion
            errorHandler(it)
        }
        return job
    }

    fun clear() = jobs.forEach { it.cancel() }
}