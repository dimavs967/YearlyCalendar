package com.yearly.calendar.pager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Custom ViewPager that allows swiping to be disabled.
 */
class CalendarViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): ViewPager(context, attrs)  {

    private var pagingEnabled = true

    /**
     * enable/disable viewpager scroll
     *
     * @param pagingEnabled false to disable paging, true for paging (default)
     */
    fun setPagingEnabled(pagingEnabled: Boolean) {
        this.pagingEnabled = pagingEnabled
    }

    /**
     * @return is this viewpager allowed to page
     */
    fun isPagingEnabled(): Boolean = pagingEnabled

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return pagingEnabled && super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return pagingEnabled && super.onTouchEvent(ev)
    }

    /**
     * disables scrolling vertically when paging disabled, fixes scrolling
     * for nested [android.support.v4.view.ViewPager]
     */
    override fun canScrollVertically(direction: Int): Boolean {
        return pagingEnabled && super.canScrollVertically(direction)
    }

    /**
     * disables scrolling horizontally when paging disabled, fixes scrolling
     * for nested [android.support.v4.view.ViewPager]
     */
    override fun canScrollHorizontally(direction: Int): Boolean {
        return pagingEnabled && super.canScrollHorizontally(direction)
    }

}