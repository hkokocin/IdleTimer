package com.kokocinski.timer

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kokocinski.toolkit.androidExtensions.start

const val TIMER_ID = "TIMER_ID"

class TimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_activity)
    }
}

fun editTimer(id: Long): (Activity) -> Unit = {
    it.start<TimerActivity> {
        putExtra(TIMER_ID, id)
    }
}