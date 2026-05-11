package com.example.uniforeventos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetalhesLivroActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)
        reservarLivro()
        voltar()
        configurarBottomNav()
    }

    private fun reservarLivro(){
        val botaoReservar = findViewById<LinearLayout>(R.id.btnReservar)
        botaoReservar.setOnClickListener {
            startActivity(Intent(this, ConfirmarReservaActivity::class.java))
        }
    }

    private fun voltar(){
        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener { finish() }
    }

    fun abrir(contexto: Context){
        val intent = Intent(contexto, DetalhesLivroActivity::class.java)
        contexto.startActivity(intent)
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