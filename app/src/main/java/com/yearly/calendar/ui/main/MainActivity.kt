package com.yearly.calendar.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yearly.calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.buttonSet.setOnClickListener {

        }
    }

    override fun onDestroy() {
        binding.calendarView.clear()
        _binding = null
        super.onDestroy()
    }

}