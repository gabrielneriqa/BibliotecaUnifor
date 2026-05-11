package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacao_check)
        configurarBottomNav()
        limparNotificacao()
    }

    private fun limparNotificacao() {
        findViewById<ImageView>(R.id.checkStatus).setOnClickListener {
            setContentView(R.layout.activity_notificacao_vazia)
            // BottomNav precisa ser reconfigurado após cada troca de layout
            configurarBottomNav()
        }
    }

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_notifications

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_notifications -> true
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