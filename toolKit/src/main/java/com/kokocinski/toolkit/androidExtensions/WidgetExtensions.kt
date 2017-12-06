package com.kokocinski.toolkit.androidExtensions

import android.support.v4.view.ViewPager
import android.widget.EditText
import android.widget.Spinner
import com.kokocinski.toolkit.androidExtensions.listeners.ItemSelectedListener
import com.kokocinski.toolkit.androidExtensions.listeners.PageChangedListener
import com.kokocinski.toolkit.androidExtensions.listeners.TextChangeListener

fun EditText.afterTextChanged(callback: (String) -> Unit): TextChangeListener {
    val listener = TextChangeListener(afterChanged = callback)
    addTextChangedListener(listener)
    return listener
}

fun EditText.onTextChanged(editText: EditText, callback: (CharSequence, Int, Int, Int) -> Unit): TextChangeListener {
    val listener = TextChangeListener(onChanged = callback)

    editText.addTextChangedListener(listener)
    return listener
}

fun EditText.beforeTextChanged(editText: EditText,
                               callback: (CharSequence, Int, Int, Int) -> Unit): TextChangeListener {
    val listener = TextChangeListener(beforeChanged = callback)
    editText.addTextChangedListener(listener)
    return listener
}

fun <T> Spinner.onItemSelected(callback: (item: T) -> Unit) {
    onItemSelectedListener = ItemSelectedListener(this, callback)
}

fun ViewPager.onPageSelected(callback: (Int) -> Unit): PageChangedListener {
    val pageChangedListener = PageChangedListener(callback)
    addOnPageChangeListener(pageChangedListener)
    return pageChangedListener
}

fun ViewPager.onScrollStateChanged(callback: (Int) -> Unit): PageChangedListener {
    val pageChangedListener = PageChangedListener(onPageScrollStateChanged = callback)
    addOnPageChangeListener(pageChangedListener)
    return pageChangedListener
}

fun ViewPager.onPageScrolled(callback: (Int, Float, Int) -> Unit): PageChangedListener {
    val pageChangedListener = PageChangedListener(onPageScrolled = callback)
    addOnPageChangeListener(pageChangedListener)
    return pageChangedListener
}