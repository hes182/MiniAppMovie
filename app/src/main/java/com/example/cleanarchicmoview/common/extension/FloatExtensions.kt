package com.example.cleanarchicmoview.common.extension

import android.content.res.Resources.getSystem

val Float.toDP: Float get() = (this / getSystem().displayMetrics.density)
val Float.toPx: Float get() = (this * getSystem().displayMetrics.density)