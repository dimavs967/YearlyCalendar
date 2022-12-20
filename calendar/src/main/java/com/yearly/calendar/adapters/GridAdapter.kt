package com.yearly.calendar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.yearly.calendar.R
import com.yearly.calendar.models.MonthModel
import com.yearly.calendar.utils.CalendarProperties

class GridAdapter(
    private val context: Context,
    private val properties: CalendarProperties
) : BaseAdapter() {

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = properties.months.size

    override fun getItem(position: Int): MonthModel = properties.months[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val item = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(
                R.layout.item_month,
                viewGroup,
                false
            )

        item.findViewById<TextView>(R.id.calendar_month).let {
            getItem(position).position = position
            it.text = getItem(position).month
            it.setPadding(0, 16, 0, 16)

            if (properties.months[position].inRange)
                it.setBackgroundResource(properties.itemSelected)

            if (properties.months[position].rangeEdge == true) {
                it.setTextColor(properties.getColor(R.color.white))
                it.setBackgroundResource(properties.itemSelectedEdge)
            }

            properties.initListener()

            it.setOnClickListener {
                properties.itemListener?.invoke(getItem(position))
                notifyDataSetChanged()
            }
        }

        return item
    }


}