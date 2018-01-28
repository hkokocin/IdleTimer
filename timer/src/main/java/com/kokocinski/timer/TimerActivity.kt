package com.kokocinski.timer

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import com.kokocinski.toolkit.android.BaseActivity
import com.kokocinski.toolkit.androidExtensions.startForResult

const val TIMER_ID = "TIMER_ID"
const val REQUEST_EDIT_TIMER = 1001

class TimerActivity : BaseActivity(R.menu.timer) {

    override val injector by lazy { timerScope(this) }
    private val view by inject<TimerView>()
    private val viewModel by inject<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view, R.layout.timer_activity)

        if (savedInstanceState == null)
            viewModel.dispatch(LoadTimerAction(intent.getLongExtra(TIMER_ID, 0L)))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        view.onOptionsItemSelected(item.itemId)
        return true
    }

    override fun onBackPressed() {
        viewModel.dispatch(StoreTimerAction())
    }
}

fun editTimer(id: Long): (Activity) -> Unit = {
    it.startForResult<TimerActivity>(REQUEST_EDIT_TIMER) {
        putExtra(TIMER_ID, id)
    }
}