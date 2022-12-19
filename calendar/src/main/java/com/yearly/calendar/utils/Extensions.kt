package com.yearly.calendar.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.yearly.calendar.R

fun Context.getMonths(): Array<out String> {
    return this.resources.getStringArray(R.array.material_calendar_months_array)
}

fun Context.color(color: Int): Int = ContextCompat.getColor(this, color)