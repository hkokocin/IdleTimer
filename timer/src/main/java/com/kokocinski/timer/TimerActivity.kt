package com.kokocinski.timer

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kokocinski.toolkit.androidExtensions.start

const val TIMER_ID = "TIMER_ID"

class TimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_activity)
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else              -> return false
        }

        return true
    }
}

fun editTimer(id: Long): (Activity) -> Unit = {
    it.start<TimerActivity> {
        putExtra(TIMER_ID, id)
    }
}