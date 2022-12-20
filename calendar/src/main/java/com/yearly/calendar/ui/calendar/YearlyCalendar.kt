package com.yearly.calendar.ui.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.yearly.calendar.R
import com.yearly.calendar.adapters.CalendarPagerAdapter
import com.yearly.calendar.databinding.CalendarHeaderBinding
import com.yearly.calendar.pager.CalendarViewPager
import com.yearly.calendar.utils.CalendarProperties

class YearlyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), IYearlyCalendar {

    private var properties: CalendarProperties = CalendarProperties(context)

    private val headerChild: View? get() = getChildAt(0)
    private val calendarChild: View? get() = getChildAt(1)

    private var _headerBinding: CalendarHeaderBinding? = null
    private val headerBinding get() = _headerBinding!!

    private var pagerAdapter: CalendarPagerAdapter? = null
    private var viewPager: CalendarViewPager? = null

    override fun getCalendarProperties(): CalendarProperties = properties

    init {
        initBinding()

        checkAttributes(attrs, defStyleAttr)
        setLayoutParams()
        setViewPager()
        addViews()

        initHeader()
    }

    // Check XML attributes
    private fun checkAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(attrs, R.styleable.YearlyCalendar, defStyleAttr) {
            setArrowsIcon(getResourceId(R.styleable.YearlyCalendar_arrowIcon, R.drawable.ic_yearly_calendar_arrow))
            setArrowsPadding(getDimensionPixelOffset(R.styleable.YearlyCalendar_arrowsPadding, 0))
            setArrowsBackground(getResourceId(R.styleable.YearlyCalendar_arrowsBackground, R.drawable.bg_arrow))
        }
    }

    // View init

    private fun addViews() {
        addView(headerBinding.root, headerBinding.root.layoutParams)
        addView(viewPager, viewPager?.layoutParams)
    }

    private fun initBinding() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _headerBinding = CalendarHeaderBinding.inflate(inflater)
    }

    private fun setLayoutParams() {
        headerBinding.header.layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    // Header methods

    private fun initHeader() {
        headerBinding.year.text = properties.year.toString()

        headerBinding.buttonPrevious.setOnClickListener {
            properties.year -= 1
            viewPager?.currentItem = viewPager?.currentItem?.minus(1) ?: 0
            headerBinding.year.text = properties.year.toString()
        }

        headerBinding.buttonForward.setOnClickListener {
            properties.year += 1
            viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0
            headerBinding.year.text = properties.year.toString()
        }
    }

    override fun setArrowsIcon(drawable: Int) {
        headerBinding.buttonPrevious.setImageDrawable(ContextCompat.getDrawable(context, drawable))
        headerBinding.buttonForward.setImageDrawable(ContextCompat.getDrawable(context, drawable))
    }

    override fun setArrowsBackground(drawable: Int) {
        headerBinding.buttonPrevious.setBackgroundResource(drawable)
        headerBinding.buttonForward.setBackgroundResource(drawable)
    }

    override fun setArrowsPadding(padding: Int) {
        headerBinding.buttonPrevious.setPadding(padding)
        headerBinding.buttonForward.setPadding(padding)
    }

    // Calendar viewPager methods

    // todo: refactor
    private fun setViewPager() {
        viewPager = CalendarViewPager(context)
        pagerAdapter = CalendarPagerAdapter(properties)
        viewPager?.id = generateViewId()

        viewPager?.adapter = pagerAdapter

        viewPager?.offscreenPageLimit = properties.offscreenLimit
        viewPager?.currentItem = properties.offscreenLimit
        viewPager?.setPadding(0, 20, 0, 0)
        viewPager?.setPagingEnabled(false)

        viewPager?.layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT).also {
            it.topMargin = headerBinding.root.layoutParams.height
        }
    }

    // ViewGroup methods

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var x = paddingLeft + (headerChild?.marginLeft ?: 0)
        var y = paddingTop + (headerChild?.marginTop ?: 0)

        headerChild?.layout(
            x,
            y,
            x + (headerChild?.measuredWidth ?: 0),
            y + (headerChild?.measuredHeight ?: 0)
        )

        x = paddingLeft + (calendarChild?.marginLeft ?: 0)
        y = paddingTop + (headerChild?.height ?: 0)

        calendarChild?.layout(
            x,
            y,
            x + (calendarChild?.measuredWidth ?: 0),
            y + (calendarChild?.measuredHeight ?: 0)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxHeight = 0
        var maxWidth = 0

        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp = child.layoutParams
                val childRight = lp.width + child.measuredWidth
                val childBottom = lp.height + child.measuredHeight
                maxWidth += maxWidth.coerceAtLeast(childRight)
                maxHeight += maxHeight.coerceAtLeast(childBottom)
            }
        }

        // Account for padding too
        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom

        // Check against minimum height and width
        maxHeight = maxHeight.coerceAtLeast(suggestedMinimumHeight)
        maxWidth = maxWidth.coerceAtLeast(suggestedMinimumWidth)

        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear()
    }

    override fun clear() {
        _headerBinding = null
        pagerAdapter = null
        viewPager = null
    }

    private companion object {
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    }

}