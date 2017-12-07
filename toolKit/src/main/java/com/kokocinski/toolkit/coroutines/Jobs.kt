package com.kokocinski.toolkit.coroutines

import kotlinx.coroutines.experimental.*
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

    fun interval(
            context: CoroutineContext = DefaultDispatcher,
            delay: Long = 1000L,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            errorHandler: (Throwable) -> Unit = {},
            block: suspend CoroutineScope.() -> Unit
    ): Job {

        if (delay == 0L) throw IllegalAccessException("delay should not be 0")

        val job = async (context, start) {
            while(true){
                block()
                delay(delay)
            }
        }
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