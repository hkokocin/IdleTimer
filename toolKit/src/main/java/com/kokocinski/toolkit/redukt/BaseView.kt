package com.kokocinski.toolkit.redukt

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.content.res.Resources
import android.support.annotation.IdRes
import android.view.View
import com.kokocinski.toolkit.androidExtensions.getAnyResource
import com.kokocinski.toolkit.androidExtensions.getColorInt
import kotlin.reflect.KClass

abstract class BaseView(vararg subViews: BaseView) : LifecycleObserver {

    protected lateinit var root: View

    val context: Context get() = root.context

    private var subViews = subViews.toList()

    fun setRootView(view: View) {
        root = view
        subViews.forEach { it.root = view }
        onViewCreated()
    }

    fun bindToLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        subViews.forEach { lifecycle.addObserver(it) }
    }

    protected open fun onViewCreated() {}

    protected fun <T : View> viewId(@IdRes id: Int): Lazy<T> = lazy { root.findViewById<T>(id) }

    inline protected fun <reified T : Any> resourceId(resourcesId: Int) = lazy {
        getResource(resourcesId, T::class)
    }

    protected fun colorResource(resourcesId: Int, theme: Resources.Theme? = null) = lazy {
        root.resources.getColorInt(resourcesId, theme)
    }

    fun <T : Any> getResource(
            resourcesId: Int,
            type: KClass<T>
    ) = getAnyResource(
            root.resources,
            resourcesId,
            type
    )

    protected fun dimensionInPixels(resourcesId: Int) = lazy {
        root.resources.getDimensionPixelSize(resourcesId)
    }
}