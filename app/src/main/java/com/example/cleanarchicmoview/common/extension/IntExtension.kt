package com.example.cleanarchicmoview.common.extension

import android.content.Context
import android.content.res.Resources.getSystem
import com.example.cleanarchicmoview.R

val Int.toDP: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()

fun Int.runTimeToReadableDuration(context: Context) : String {
    val hour: Int = this / 60
    val minutes: Int = this % 60
    return  context.getString(R.string.hourAndMinWithParam, hour, minutes)
}