package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ConfirmarReservaActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_reserva)

        reservaConfirmada()
        reservaCancelada()
        configurarBottomNav()
    }

    private fun reservaConfirmada(){
        findViewById<TextView>(R.id.btnSim).setOnClickListener {
            startActivity(Intent(this, ReservaConfirmada::class.java))
        }
    }

    private fun reservaCancelada(){
        findViewById<TextView>(R.id.btnCancelar).setOnClickListener {
            finish()
        }
    }

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_books

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