package com.example.sampleappfortest.common.extensions

import android.view.View

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.translucent() {
    this?.alpha = 0.5f
}

fun View?.opaque() {
    this?.alpha = 1.0f
}

fun View.isVisible(): Boolean {
    if (this.visibility == View.VISIBLE) {
        return true
    }
    return false
}

fun View.setViewVisibility(value:Boolean)
{
    visibility = if (value) View.VISIBLE else View.GONE
}
fun View.showHide(show: Boolean) {
    if (show) this.visible() else this.gone()
}

fun View.isGone(): Boolean {
    if (this.visibility == View.GONE) {
        return true
    }
    return false
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

public inline var View.isvisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }