package com.kokocinski.toolkit

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.kokocinski.toolkit.coroutines.Jobs

fun toolKitModule() = Kodein.Module {
    bind<Jobs>() with provider { Jobs() }
    bind<SystemTimerProvider>() with provider { SystemTimerProvider() }
}
