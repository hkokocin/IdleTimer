package com.kokocinski.timer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.EditText
import android.widget.NumberPicker
import com.kokocinski.toolkit.androidExtensions.afterTextChanged
import com.kokocinski.toolkit.redukt.BaseView

class TimerView(
        private val viewModel: TimerViewModel
) : BaseView() {

    private val etName: EditText by viewId(R.id.et_name)
    private val npHours: NumberPicker by viewId(R.id.np_hours)
    private val npMinutes: NumberPicker by viewId(R.id.np_minutes)
    
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun bindView(){
        etName.afterTextChanged { viewModel.dispatch(NameChangedEvent(etName.text.toString())) }
        
        npHours.setOnValueChangedListener { _, _, value -> viewModel.dispatch(HoursChangedEvent(value)) }
        npHours.minValue = 0
        npHours.maxValue = 168

        npMinutes.setOnValueChangedListener { _, _, value -> viewModel.dispatch(MinutesChangedEvent(value)) }
        npMinutes.minValue = 0
        npMinutes.maxValue = 59
    }
    
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun bindViewModel(lifecycleOwner: LifecycleOwner) = viewModel.observe(lifecycleOwner.lifecycle) {
        
    }
}