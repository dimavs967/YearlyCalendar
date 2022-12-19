package com.yearly.calendar.utils

import android.content.Context
import android.widget.Toast
import com.yearly.calendar.R

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Context.getMonths(): Array<out String> {
    return this.resources.getStringArray(R.array.material_calendar_months_array)
}