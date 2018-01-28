package com.kokocinski.timer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.NumberPicker
import com.kokocinski.toolkit.androidExtensions.afterTextChanged
import com.kokocinski.toolkit.redukt.BaseView

class TimerView(
        private val viewModel: TimerViewModel,
        private val activity: AppCompatActivity
) : BaseView() {

    private val toolbar: Toolbar by viewId(R.id.toolbar)
    private val etName: EditText by viewId(R.id.et_name)
    private val npHours: NumberPicker by viewId(R.id.np_hours)
    private val npMinutes: NumberPicker by viewId(R.id.np_minutes)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initActionBar() {
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun bindView() {
        etName.afterTextChanged { viewModel.dispatch(NameChangedAction(etName.text.toString())) }

        npHours.setOnValueChangedListener { _, _, value -> viewModel.dispatch(HoursChangedAction(value)) }
        npHours.minValue = 0
        npHours.maxValue = 168

        npMinutes.setOnValueChangedListener { _, _, value -> viewModel.dispatch(MinutesChangedAction(value)) }
        npMinutes.minValue = 0
        npMinutes.maxValue = 59
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun bindViewModel(lifecycleOwner: LifecycleOwner) = viewModel.observe(lifecycleOwner.lifecycle) {
        etName.setText(it.name)
        npHours.post {
            npHours.value = it.hours
            npMinutes.value = it.minutes
        }
        it.command(activity)
    }

    fun onOptionsItemSelected(itemId: Int) {
        when (itemId) {
            android.R.id.home -> viewModel.dispatch(StoreTimerAction())
            R.id.delete       -> viewModel.dispatch(DeleteTimerAction())
        }
    }
}