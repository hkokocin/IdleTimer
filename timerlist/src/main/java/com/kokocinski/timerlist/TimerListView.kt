package com.kokocinski.timerlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.kokocinski.timer.REQUEST_EDIT_TIMER
import com.kokocinski.toolkit.redukt.BaseView
import de.welt.widgetadapter.WidgetAdapter

class TimerListView(
        private val viewModel: TimerListViewModel,
        private val adapter: WidgetAdapter,
        private val activity: AppCompatActivity,
        timerWidgetProvider: () -> TimerWidget
) : BaseView() {

    private val toolbar: Toolbar by viewId(R.id.toolbar)
    private val rvTimers: RecyclerView by viewId(R.id.rv_timers)

    init {
        adapter.addWidget(timerWidgetProvider)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initToolbar() {
        activity.setSupportActionBar(toolbar)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initialize(lifecycleOwner: LifecycleOwner) {
        initRecyclerView()
        bindViewModel(lifecycleOwner.lifecycle)
    }

    fun onOptionsItemSelected(itemId: Int) {
        if (itemId == R.id.new_timer) viewModel.dispatch(EditTimerAction(0))
    }

    private fun bindViewModel(lifecycle: Lifecycle) = viewModel.observe(lifecycle) {
        adapter.updateItems(it.timers)
        it.command(activity)
    }

    private fun initRecyclerView() {
        rvTimers.adapter = adapter
        rvTimers.layoutManager = LinearLayoutManager(activity)
    }

    fun onActivityResult(requestCode: Int) {
        if(requestCode == REQUEST_EDIT_TIMER) viewModel.dispatch(UpdateTimersAction())
    }
}