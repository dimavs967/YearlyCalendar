package com.yearly.calendar.models

data class MonthModel(
    val month: String,
    var inRange: Boolean = false,
    var rangeEdge: Boolean? = null
)