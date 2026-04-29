package com.example.uniforeventos

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class DateTimeFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_date_time_filter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnApply).setOnClickListener {
            // Logic to apply filter would go here
            finish()
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val calendar = Calendar.getInstance()
        calendar.set(2026, Calendar.APRIL, 12)
        calendarView.date = calendar.timeInMillis
    }
}