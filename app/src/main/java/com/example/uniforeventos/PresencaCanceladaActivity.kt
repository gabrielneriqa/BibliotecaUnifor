package com.example.uniforeventos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class PresencaCanceladaActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenca_cancelada)
        sair()
        configurarBottomNav()
    }

    private fun sair(){
        findViewById<MaterialCardView>(R.id.btnVoltar).setOnClickListener {
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