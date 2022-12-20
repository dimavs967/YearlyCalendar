package com.yearly.calendar.utils

import android.content.Context
import androidx.annotation.DrawableRes
import com.yearly.calendar.R
import com.yearly.calendar.listeners.OnItemClickListener
import com.yearly.calendar.listeners.OnMonthSelectListener
import com.yearly.calendar.models.MonthModel
import com.yearly.calendar.models.Range

class CalendarProperties(
    context: Context
) {

    var offscreenLimit = 20

    var year: Int = getYear()

    val months = arrayListOf<MonthModel>()

    var flag: Boolean = true

    var startIndex: Int? = null

    var endIndex: Int? = null

    init {
        context.getMonths().forEach { months.add(MonthModel(0, it, false)) }
    }

    var clickListener: OnMonthSelectListener? = null

    var itemListener: OnItemClickListener? = null

    @DrawableRes
    var itemSelected = R.drawable.bg_month_selected

    @DrawableRes
    var itemSelectedEdge = R.drawable.bg_month_selected_edge

    private fun checkRange(): Boolean {
        return startIndex != null && endIndex != null
    }

    fun initListener() {
        itemListener = {
            if (flag) {
                clearRange()
                startIndex = it.position
                flag = false
            } else {
                endIndex = it.position
                flag = true
            }
            it.inRange = true
            updateUi()
        }
    }

    private fun updateUi() {
        if (checkRange()) {
            if (endIndex!! < startIndex!!)
                startIndex = endIndex.also { endIndex = startIndex }
            for (i in 0 until months.size) {
                months[i].inRange = i in startIndex!!..endIndex!!
            }

            months[startIndex!!].rangeEdge = true
            months[endIndex!!].rangeEdge = true

            clickListener?.invoke(
                Range(
                    "${months[startIndex!!].month}, $year",
                    "${months[endIndex!!].month}, $year"
                )
            )
        }
    }

    fun clearRange() {
        startIndex = null
        endIndex = null

        months.onEach {
            it.inRange = false
            it.rangeEdge = false
        }
    }

}