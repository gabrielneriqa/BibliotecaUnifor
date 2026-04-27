package com.example.uniforeventos

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate?,
    val dayNumber: String,
    val isCurrentMonth: Boolean,
    val isReserved: Boolean = false,
    val isSelected: Boolean = false,
    val isRangeStart: Boolean = false,
    val isRangeEnd: Boolean = false,
    val isInRange: Boolean = false
)