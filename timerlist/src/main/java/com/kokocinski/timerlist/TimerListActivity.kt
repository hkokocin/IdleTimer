package com.kokocinski.timerlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.kokocinski.toolkit.android.BaseActivity

class TimerListActivity : BaseActivity(R.menu.timer_list) {

    override val injector by lazy { timerListScope(this) }
    private val view by inject<TimerListView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view, R.layout.timer_list_activity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        view.onOptionsItemSelected(item.itemId)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)
            view.onActivityResult(requestCode)
    }
}
