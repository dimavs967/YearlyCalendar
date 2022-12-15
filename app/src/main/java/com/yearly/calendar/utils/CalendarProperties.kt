package com.yearly.calendar.utils

import android.content.Context
import com.yearly.calendar.listeners.OnMonthSelectListener
import com.yearly.calendar.models.MonthModel

class CalendarProperties(
    context: Context
) {

    var year: Int = getYear()

    val months = arrayListOf<MonthModel>()

    init {
        context.getMonths().forEach {
            months.add(MonthModel(it, false))
        }
    }

    var clickListener: OnMonthSelectListener? = null

    // ?
    fun setRangeEdge(i: Int) {
        months[i].rangeEdge = true
    }

}