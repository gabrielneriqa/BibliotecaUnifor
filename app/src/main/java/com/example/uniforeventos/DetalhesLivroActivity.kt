package com.example.uniforeventos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetalhesLivroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIVRO_ID = "livro_id"

        fun abrir(context: Context, livroId: Long) {
            val intent = Intent(context, DetalhesLivroActivity::class.java)
            intent.putExtra(EXTRA_LIVRO_ID, livroId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)

        val livroId = intent.getLongExtra(EXTRA_LIVRO_ID, -1L)

        reservarLivro(livroId)
        voltar()
        configurarBottomNav()
    }

    private fun reservarLivro(livroId: Long) {
        val botaoReservar = findViewById<LinearLayout>(R.id.btnReservar)
        botaoReservar.setOnClickListener {
            val intent = Intent(this, ConfirmarReservaActivity::class.java)
            intent.putExtra(ConfirmarReservaActivity.EXTRA_LIVRO_ID, livroId)
            startActivity(intent)
        }
    }

    private fun voltar() {
        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener { finish() }
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