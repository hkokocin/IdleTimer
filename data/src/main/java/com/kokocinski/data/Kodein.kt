package com.kokocinski.data

import android.app.Application
import com.evernote.android.job.JobManager
import com.github.salomonbrys.kodein.*
import com.kokocinski.data.jobs.JobDataRepository
import com.kokocinski.toolkit.toolKitModule
import io.objectbox.Box
import io.objectbox.BoxStore

private lateinit var boxStore: BoxStore
private var dataModule: Kodein.Module? = null

fun dataModule() = dataModule ?: throw IllegalStateException("you have to initialize the dataModule first ")

fun initDataModule(app: Application) {

    if (dataModule != null) throw IllegalStateException("dataModule already initialized")

    initBoxStore(app)

    dataModule = Kodein.Module {
        import(toolKitModule(), true)

        bind<JobManager>() with provider { JobManager.create(app) }
        bind<Box<Timer>>() with provider { boxStore.boxFor(Timer::class.java) }
        bind<Box<Boolean>>() with provider { boxStore.boxFor(Boolean::class.java) }
        bind<Box<NotificationJob>>() with provider { boxStore.boxFor(NotificationJob::class.java) }
        bind<TimerRepository>() with singleton { TimerRepository(instance(), instance()) }
        bind<JobDataRepository>() with singleton { JobDataRepository(instance(), instance(), instance()) }
        bind<ApplicationPreferences>() with singleton { ApplicationPreferences(app.getSharedPreferences("app", 0)) }
    }
}

private fun initBoxStore(app: Application) {
    boxStore = MyObjectBox
            .builder()
            .androidContext(app)
            .build()
            .apply { boxStore = this }
}