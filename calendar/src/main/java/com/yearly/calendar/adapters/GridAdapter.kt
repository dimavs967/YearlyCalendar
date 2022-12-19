package com.yearly.calendar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.yearly.calendar.R
import com.yearly.calendar.models.Range
import com.yearly.calendar.utils.CalendarProperties
import com.yearly.calendar.utils.getMonths

// todo: redo this bullshit later
class GridAdapter(
    private val context: Context,
    private val properties: CalendarProperties
) : BaseAdapter() {

    private var flag: Boolean = true
    private var startIndex: Int? = null
    private var endIndex: Int? = null

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = properties.months.size

    override fun getItem(position: Int): Any = properties.months[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        val item = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(
                R.layout.item_month,
                viewGroup,
                false
            )

        item.findViewById<TextView>(R.id.month).let {
            it.text = context.getMonths()[position]
            it.setPadding(0, 16, 0, 16)

            if (properties.months[position].inRange)
                it.setBackgroundResource(R.drawable.bg_month_selected)

            if (properties.months[position].rangeEdge == true) {
                it.setTextColor(Color.parseColor("#ffffff"))
                it.setBackgroundResource(R.drawable.bg_month_selected_edge)
            }

            initListener(it, position)
        }

        return item
    }

    private fun initListener(view: View, position: Int) {
        view.setOnClickListener {
            if (flag) {
                clearRange()
                startIndex = position
                flag = false
            } else {
                endIndex = position
                flag = true
            }

            properties.months[position].inRange = true

            updateUi()
            notifyDataSetChanged()
        }
    }

    private fun updateUi() {
        if (startIndex != null && endIndex != null) {
            if (endIndex!! < startIndex!!)
                startIndex = endIndex.also { endIndex = startIndex }
            for (i in 0 until properties.months.size) {
                properties.months[i].inRange = i in startIndex!!..endIndex!!
            }

            properties.setRangeEdge(startIndex!!)
            properties.setRangeEdge(endIndex!!)

            properties.clickListener?.invoke(
                Range(
                    "${properties.months[startIndex!!].month}, ${properties.year}",
                    "${properties.months[endIndex!!].month}, ${properties.year}"
                )
            )
        }

        notifyDataSetChanged()
    }

    private fun clearRange() {
        startIndex = null
        endIndex = null

        properties.months.onEach {
            it.inRange = false
            it.rangeEdge = false
        }

        notifyDataSetChanged()
    }

}