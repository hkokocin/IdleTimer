package com.kokocinski.toolkit.androidExtensions

import android.view.MotionEvent
import android.view.View
import com.kokocinski.toolkit.androidExtensions.listeners.AttachStateChangeListener

fun View.onClick(listener: (View) -> Unit) = setOnClickListener(listener)

fun View.onLongClick(listener: (View) -> Boolean) = setOnLongClickListener(listener)

fun View.onTouch(listener: (View, MotionEvent) -> Boolean) = setOnTouchListener(listener)

fun View.onAttachedToWindow(callback: (view: View) -> Unit): AttachStateChangeListener {
    val listener = AttachStateChangeListener(onAttached = callback)
    addOnAttachStateChangeListener(listener)
    return listener
}

fun View.onDetachedFromWindow(callback: (view: View) -> Unit): AttachStateChangeListener {
    val listener = AttachStateChangeListener(onDetached = callback)
    addOnAttachStateChangeListener(listener)
    return listener
}

fun View.onLayoutChanged(callback: () -> Unit): View.OnLayoutChangeListener {
    val listener = View.OnLayoutChangeListener{ _, _, _, _, _, _, _, _, _ -> callback() }
    addOnLayoutChangeListener(listener)
    return listener
}
