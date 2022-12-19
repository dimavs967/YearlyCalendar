package com.yearly.calendar.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yearly.calendar.databinding.ActivityMainBinding
import com.yearly.calendar.utils.toast

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cal = binding.calendarView.getCalendarProperties()

        cal.clickListener = {
            this.toast("Start: ${it.startDate} / End: ${it.endDate} ")
        }

    }

    override fun onDestroy() {
        binding.calendarView.clear()
        _binding = null
        super.onDestroy()
    }
}