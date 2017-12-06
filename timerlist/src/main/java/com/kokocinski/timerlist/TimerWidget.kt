package com.kokocinski.timerlist

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import com.kokocinski.data.Timer
import de.welt.widgetadapter.Widget

class TimerWidget : Widget<TimerWidgetState>(R.layout.timer_widget) {

    private val llContainer: LinearLayout by viewId(R.id.ll_container)
    private val tvName: TextView by viewId(R.id.tv_name)
    private val tvTimer: TextView by viewId(R.id.tv_timer)

    private var timer = Timer()
    private var dispatch: (Any) -> Unit = {}

    override fun onViewCreated(view: View) {
        llContainer.setOnClickListener { dispatch(StartTimerAction(timer)) }
        llContainer.setOnLongClickListener { showPopup(); true }
    }

    override fun setData(data: TimerWidgetState) {
        timer = data.timer
        dispatch = data.dispatch

        tvName.text = data.name
        tvTimer.text = data.timeRemaining

        llContainer.isClickable = data.timerFinished
    }

    private fun showPopup() {
        val menu = PopupMenu(llContainer.context, llContainer, Gravity.END)
        menu.inflate(R.menu.timer_context_menu)
        menu.setOnMenuItemClickListener { item -> onMenuItemClicked(item.itemId) }
        menu.show()
    }

    private fun onMenuItemClicked(itemId: Int): Boolean {
        when (itemId) {
            R.id.menu_item_edit   -> dispatch(EditTimerAction(timer.id))
            R.id.menu_item_delete -> dispatch(DeleteTimerAction(timer.id))
        }

        return true
    }
}

