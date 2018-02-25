package com.kokocinski.timerlist

import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import com.kokocinski.data.Timer
import com.kokocinski.toolkit.androidExtensions.onAttachedToWindow
import com.kokocinski.toolkit.androidExtensions.onDetachedFromWindow
import com.kokocinski.toolkit.coroutines.Jobs
import de.welt.widgetadapter.Widget
import kotlinx.coroutines.experimental.android.UI

class TimerWidget(
        private val jobs: Jobs
) : Widget<TimerWidgetState>(R.layout.timer_widget) {

    private val llContainer: LinearLayout by viewId(R.id.ll_container)
    private val tvName: TextView by viewId(R.id.tv_name)
    private val tvTimer: TextView by viewId(R.id.tv_timer)

    private var timer = Timer()
    private var dispatch: (Any) -> Unit = {}

    private val animation = AlphaAnimation(0.5f, 1.0f).apply {
        duration = 500
        startOffset = 0
        interpolator = AccelerateDecelerateInterpolator()
        repeatMode = Animation.REVERSE
        repeatCount = Animation.INFINITE
    }

    override fun onViewCreated(view: View) {
        llContainer.setOnClickListener { if (timer.isFinished) dispatch(StartTimerAction(timer)) }
        llContainer.setOnLongClickListener { showPopup(); true }

        //todo find a better way
        llContainer.onAttachedToWindow { jobs.interval(UI, 1000) { updateTimer() } }
        llContainer.onDetachedFromWindow { jobs.clear() }
    }

    override fun setData(data: TimerWidgetState) {
        timer = data.timer
        dispatch = data.dispatch

        tvName.text = data.name
        updateTimer()
    }

    private fun updateTimer() {
        tvTimer.text = timer.getTimeRemainingString()
        if (timer.isFinished)
            tvTimer.startAnimation(animation)
        else
            animation.reset()
    }

    private fun showPopup() {
        val menu = PopupMenu(llContainer.context, llContainer, Gravity.END)
        menu.inflate(R.menu.timer_context_menu)
        menu.setOnMenuItemClickListener { item -> onMenuItemClicked(item.itemId) }
        menu.show()
    }

    private fun onMenuItemClicked(itemId: Int): Boolean {
        when (itemId) {
            R.id.menu_item_edit    -> dispatch(EditTimerAction(timer.id))
            R.id.menu_item_delete  -> dispatch(DeleteTimerAction(timer.id))
            R.id.menu_item_restart -> dispatch(RestartTimerAction(timer))
        }

        return true
    }
}

