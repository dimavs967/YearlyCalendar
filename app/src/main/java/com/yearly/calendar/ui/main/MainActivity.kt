package com.yearly.calendar.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yearly.calendar.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCalendar()
    }

    private fun initCalendar() {

    }

}