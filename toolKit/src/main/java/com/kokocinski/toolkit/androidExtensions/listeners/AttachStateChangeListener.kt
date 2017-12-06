package com.kokocinski.toolkit.androidExtensions.listeners

import android.view.View

class AttachStateChangeListener(
        private val onAttached: (View) -> Unit = {},
        private val onDetached: (View) -> Unit = {}
) : View.OnAttachStateChangeListener {
    override fun onViewDetachedFromWindow(view: View) {
        onDetached(view)
    }

    override fun onViewAttachedToWindow(view: View) {
        onAttached(view)
    }
}