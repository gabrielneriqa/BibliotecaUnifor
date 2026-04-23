package com.example.uniforeventos

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CreateEventActivity : AppCompatActivity() {

    private lateinit var dateRow: View
    private lateinit var tvDateValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_evento)

        dateRow = findViewById(R.id.dateRow)
        tvDateValue = findViewById(R.id.tvDateValue)

        dateRow.setOnClickListener {
            abrirCalendario()
        }
    }

    private fun abrirCalendario() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecione a data")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { utcMillis ->
            val formato = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("pt", "BR"))
            formato.timeZone = TimeZone.getTimeZone("UTC")
            tvDateValue.text = formato.format(Date(utcMillis))
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

}