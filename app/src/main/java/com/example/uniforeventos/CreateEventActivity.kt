package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        criarEvento()
        configurarBottomNav()
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

    private fun criarEvento(){
        findViewById<MaterialButton>(R.id.btnAgendarEvento).setOnClickListener {
            startActivity(Intent(this, EventoCriadoActivity::class.java))
            finish()
        }
    }


    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_notifications -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }
                R.id.nav_books -> {
                    startActivity(Intent(this, LivrosReservadosActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ContaUsuarioActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

}