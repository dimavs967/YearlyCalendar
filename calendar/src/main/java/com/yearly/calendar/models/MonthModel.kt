package com.yearly.calendar.models

data class MonthModel(
    var position: Int = 0,
    val month: String,
    var inRange: Boolean = false,
    var rangeEdge: Boolean? = null
)