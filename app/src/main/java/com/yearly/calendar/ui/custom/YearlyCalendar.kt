package com.yearly.calendar.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.yearly.calendar.R
import com.yearly.calendar.databinding.CustomCalendarBinding
import com.yearly.calendar.databinding.ExampleBinding

/**
 * Custom yearly calendar to choose a date range by month
 */

class YearlyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), IYearlyCalendar {

    private var _headerBinding: CustomCalendarBinding? = null
    private val headerBinding get() = _headerBinding!!

    private var _exampleBinding: ExampleBinding? = null
    private val exampleBinding get() = _exampleBinding!!

    private var headerTitle: Int = 2022 // todo: set current year by default later

    init {
        initBinding()

        checkAttributes(attrs, defStyleAttr)
        setLayoutParams()
        addViews()

        initHeader()
    }

    private fun checkAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(attrs, R.styleable.YearlyCalendar, defStyleAttr) {
            setHeaderIcon(getResourceId(R.styleable.YearlyCalendar_arrowIcon, R.drawable.ic_arrow))
        }
    }

    private fun initBinding() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        _headerBinding = CustomCalendarBinding.inflate(inflater)
        _exampleBinding = ExampleBinding.inflate(inflater)
    }

    private fun setLayoutParams() {
        exampleBinding.exampleLayout.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        headerBinding.header.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    private fun addViews() {
        addView(headerBinding.root)
//        addView(exampleBinding.root)
    }

    // todo: fix the child's relative location
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp = child.layoutParams
                val childLeft: Int = paddingLeft + lp.width
                val childTop: Int = paddingTop + lp.height
                child.layout(
                    childLeft, childTop,
                    childLeft + child.measuredWidth,
                    childTop + child.measuredHeight
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxHeight = 0
        var maxWidth = 0

        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                var childRight: Int
                var childBottom: Int
                val lp = child.layoutParams
                childRight = lp.width + child.measuredWidth
                childBottom = lp.height + child.measuredHeight
                maxWidth = maxWidth.coerceAtLeast(childRight)
                maxHeight = maxHeight.coerceAtLeast(childBottom)
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

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Set the icon with the direction to the right.
     * The left icon is automatically rotated.
     */
    override fun setHeaderIcon(drawable: Int) {
        headerBinding.buttonPrevious.setImageDrawable(ContextCompat.getDrawable(context, drawable))
        headerBinding.buttonNext.setImageDrawable(ContextCompat.getDrawable(context, drawable))
    }

    private fun initHeader() {
        headerBinding.year.text = headerTitle.toString()

        headerBinding.buttonPrevious.setOnClickListener {
            headerTitle -= 1
            headerBinding.year.text = headerTitle.toString()
        }

        headerBinding.buttonNext.setOnClickListener {
            headerTitle += 1
            headerBinding.year.text = headerTitle.toString()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear()
    }

    override fun clear() {
        _headerBinding = null
        _exampleBinding = null
    }

    private companion object {
        const val TAG = "CalendarView"
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    }

}