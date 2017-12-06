package com.kokocinski.toolkit.android

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider

abstract class BaseActivity(private val optionsMenu: Int = 0) : AppCompatActivity() {

    abstract val injector: Kodein

    protected inline fun <reified T : Any> inject() = lazy { injector.instance<T>() }
    protected inline fun <reified T : Any> provide() = lazy { injector.provider<T>() }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (optionsMenu != 0)
            menuInflater.inflate(optionsMenu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}