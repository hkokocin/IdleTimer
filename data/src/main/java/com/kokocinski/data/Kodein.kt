package com.kokocinski.data

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.kokocinski.toolkit.toolKitModule
import io.objectbox.Box
import io.objectbox.BoxStore

private lateinit var boxStore: BoxStore
private var dataModule: Kodein.Module? = null

val isDataModuleInitialized = dataModule != null

fun dataModule() = dataModule ?: throw IllegalStateException("you have to initialize the dataModule first ")

fun initDataModule(app: Application) {

    if (dataModule != null) throw IllegalStateException("dataModule already initialized")

    initBoxStore(app)

    dataModule = Kodein.Module {
        import(toolKitModule(), true)

        bind<Box<Timer>>() with provider { boxStore.boxFor(Timer::class.java) }
        bind<Box<Boolean>>() with provider { boxStore.boxFor(Boolean::class.java) }
        bind<TimerRepository>() with singleton { TimerRepository(instance(), instance(), instance()) }
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