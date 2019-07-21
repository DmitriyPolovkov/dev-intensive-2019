package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    if (isKeyboardOpen()) {
        val focus = this.currentFocus
        focus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                focus.windowToken,
                0
            )
        }
    }
}

fun Activity.isKeyboardClosed(): Boolean {
    return isKeyboardOpen().not()
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val rootView = this.window.decorView
    rootView.getWindowVisibleDisplayFrame(r)
    return (rootView.height - r.bottom) > rootView.height * 0.15
}
