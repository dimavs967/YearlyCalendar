package com.yearly.calendar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.viewpager.widget.PagerAdapter
import com.yearly.calendar.R
import com.yearly.calendar.utils.CalendarProperties

class CalendarPagerAdapter(
    private val properties: CalendarProperties
) : PagerAdapter() {

    override fun getCount(): Int = Int.MAX_VALUE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val gridAdapter = GridAdapter(container.context, properties)

        return LayoutInflater.from(container.context)
            .inflate(R.layout.calendar_view, container, false).also {
                it.findViewById<GridView>(R.id.months_grid).adapter = gridAdapter
                container.addView(it)
            }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

}