package com.kokocinski.timerlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.instance

class TimerListActivity : AppCompatActivity() {

    private val injector by lazy { timerListScope(this) }
    private val view by lazy { injector.instance<TimerListView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.createView(layoutInflater, null, false))
        lifecycle.addObserver(view)
    }
}
