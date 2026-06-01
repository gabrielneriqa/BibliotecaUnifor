package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContaUsuarioActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta_usuario)

        suporte()
        redefinirSenha()
        idioma()
        logout()
        configurarBottomNav()
    }

    private fun suporte() {
        findViewById<RelativeLayout>(R.id.rlSuporte).setOnClickListener {
            startActivity(Intent(this, SuporteActivity::class.java))
        }
    }

    private fun redefinirSenha() {
        findViewById<RelativeLayout>(R.id.rlSenha).setOnClickListener {
            startActivity(Intent(this, RedefinirSenhaActivity::class.java))
        }
    }

    private fun idioma() {
        findViewById<RelativeLayout>(R.id.rlIdiomas).setOnClickListener {
            startActivity(Intent(this, IdiomasActivity::class.java))
        }
    }

    private fun logout() {
        findViewById<RelativeLayout>(R.id.rlExit).setOnClickListener {
            startActivity(Intent(this, LogoutActivity::class.java))
        }
    }

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_profile

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
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}