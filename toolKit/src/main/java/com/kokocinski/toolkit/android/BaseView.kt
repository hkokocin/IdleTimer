package com.kokocinski.toolkit.android

import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.content.res.Resources
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kokocinski.toolkit.androidExtensions.getColorInt
import kotlin.reflect.KClass

abstract class BaseView(@LayoutRes private val layout: Int) : LifecycleObserver {
    lateinit var root: View
        private set

    val context: Context = root.context

    fun createView(inflater: LayoutInflater, parent: ViewGroup? = null, attachToRoot: Boolean = true) {
        root = inflater.inflate(layout, parent, attachToRoot)
        onViewCreated()
    }

    protected open fun onViewCreated() {}

    protected fun <T : View> viewId(@IdRes id: Int): Lazy<T> = lazy { root.findViewById<T>(id) }

    inline fun <reified T : Any> resourceId(resourcesId: Int) = lazy {
        getResource(resourcesId, T::class)
    }

    fun colorResource(resourcesId: Int, theme: Resources.Theme? = null) = lazy {
        root.resources.getColorInt(resourcesId, theme)
    }

    fun <T : Any> getResource(resourcesId: Int, type: KClass<T>) = com.kokocinski.toolkit.androidExtensions.getResource(
            root.resources,
            resourcesId,
            type)

    fun dimensionInPixels(resourcesId: Int) = lazy {
        root.resources.getDimensionPixelSize(resourcesId)
    }
}