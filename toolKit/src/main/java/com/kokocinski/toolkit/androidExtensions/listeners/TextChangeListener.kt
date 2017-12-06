package com.kokocinski.toolkit.androidExtensions.listeners

import android.text.Editable
import android.text.TextWatcher

class TextChangeListener(
        private val onChanged: (CharSequence, Int, Int, Int) -> Unit = {_, _, _, _ -> Unit},
        private val beforeChanged: (CharSequence, Int, Int, Int) -> Unit = {_, _, _, _ -> Unit},
        private val afterChanged: (String) -> Unit = {}
) : TextWatcher {

    override fun afterTextChanged(s: Editable) {
        afterChanged(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        beforeChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onChanged(s, start, before, count)
    }
}