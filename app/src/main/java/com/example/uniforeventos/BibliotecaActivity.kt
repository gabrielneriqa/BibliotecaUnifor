package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BibliotecaActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biblioteca)

        verDetalhes()
        backButton()
        verFiltro()
        configurarBottomNav()
    }

    private fun verDetalhe(){
        startActivity(Intent(this, DetalhesLivroActivity::class.java))
    }

    private fun verDetalhes(){
        findViewById<TextView>(R.id.btnDetalhes1).setOnClickListener { verDetalhe() }
        findViewById<TextView>(R.id.btnDetalhes2).setOnClickListener { verDetalhe() }
        findViewById<TextView>(R.id.btnDetalhes3).setOnClickListener { verDetalhe() }
        findViewById<TextView>(R.id.btnDetalhes4).setOnClickListener { verDetalhe() }
    }

    private fun verFiltro(){
        findViewById<LinearLayout>(R.id.llFilter).setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }
    }

    private fun backButton(){
        val botaoVoltar = findViewById<TextView>(R.id.tvVoltar)
        botaoVoltar.setOnClickListener {
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