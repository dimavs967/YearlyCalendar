package com.yearly.calendar.ui.calendar

import com.yearly.calendar.utils.CalendarProperties

interface IYearlyCalendar {

    /**
     * Get calendar properties
     */
    fun getCalendarProperties(): CalendarProperties

    /**
     * Set the icon with the direction to the right.
     * The left icon is automatically rotated.
     */
    fun setArrowsIcon(drawable: Int)

    /**
     * Set the arrows background resource
     */
    fun setArrowsBackground(drawable: Int)

    /**
     * Set the arrows paddings
     */
    fun setArrowsPadding(padding: Int)

    /**
     * Method for cleaning bindings and objects.
     * Called at onDetachedFromWindow
     */
    fun clear()

}