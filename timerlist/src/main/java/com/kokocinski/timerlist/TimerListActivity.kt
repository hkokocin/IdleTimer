package com.kokocinski.timerlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.instance
import com.kokocinski.toolkit.android.BaseActivity

class TimerListActivity : BaseActivity() {

    override val injector by lazy { timerListScope(this) }
    private val view by inject<TimerListView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view, R.layout.timer_list_activity)
    }
}
