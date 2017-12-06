package com.kokocinski.commonui

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.*
import de.welt.widgetadapter.WidgetAdapter

fun activityModule(activity: AppCompatActivity) = Kodein.Module {
    bind<Context>() with singleton { activity }
    bind<Activity>() with singleton { activity }
    bind<AppCompatActivity>() with singleton { activity }
    bind<Resources>() with singleton { activity.resources }
    bind<LayoutInflater>() with singleton { activity.layoutInflater }
}

fun thirdPartyUiModule(activity: AppCompatActivity) = Kodein.Module {
    import(activityModule(activity), true)

    bind<WidgetAdapter>() with provider { WidgetAdapter(instance()) }
}