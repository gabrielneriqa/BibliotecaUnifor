package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)

        configurarHamburger()
        configurarSidebar()
        configurarEventos()
        configurarLivros()
        configurarBottomNav()
    }

    private fun configurarHamburger() {
        findViewById<View>(R.id.ivHamburger).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun configurarSidebar() {
        findViewById<View>(R.id.btnFecharSidebar).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        findViewById<View>(R.id.menuHome).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        findViewById<View>(R.id.menuCriarEvento).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, CreateEventActivity::class.java))
        }
        findViewById<View>(R.id.menuNotificacoes).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.menuLivrosReservados).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, LivrosReservadosActivity::class.java))
        }
        findViewById<View>(R.id.menuEventosReservados).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.menuPerfil).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configurarEventos() {
        // 🔧 Substituir por dados reais da API
        val eventos = listOf(
            Evento(
                id = 1, titulo = "Inovação: futuro com IA",
                dia = "10", mes = "ABR", confirmadosTexto = "+30 confirmados",
                local = "Teatro Celina Queiroz",
                imagemResId = R.drawable.img_evento_placeholder,
                avatar1ResId = R.drawable.img_avatar_1,
                avatar2ResId = R.drawable.img_avatar_2,
                avatar3ResId = R.drawable.img_avatar_3,
                favorito = true,
                dataCompleta = "10 de Abril, 2026", horario = "19:00",
                endereco = "Teatro Celina Queiroz, Fortaleza",
                organizadorNome = "UNIFOR", organizadorCargo = "Universidade",
                organizadorImagemResId = R.drawable.img_avatar_1,
                descricao = "Um evento sobre o futuro da inteligência artificial."
            ),
            Evento(
                id = 2, titulo = "Tecnologias Emergentes",
                dia = "18", mes = "ABR", confirmadosTexto = "+10 confirmados",
                local = "Sala Raquel de Queiroz",
                imagemResId = R.drawable.img_evento_confirmado,
                avatar1ResId = R.drawable.img_avatar_1,
                avatar2ResId = R.drawable.img_avatar_2,
                avatar3ResId = R.drawable.img_avatar_3,
                favorito = false,
                dataCompleta = "18 de Abril, 2026", horario = "14:00",
                endereco = "Sala Raquel de Queiroz, UNIFOR",
                organizadorNome = "UNIFOR Tech", organizadorCargo = "Departamento de TI",
                organizadorImagemResId = R.drawable.img_avatar_2,
                descricao = "Palestra sobre as tecnologias mais emergentes."
            ),
            Evento(
                id = 3, titulo = "Workshop: Design Thinking",
                dia = "25", mes = "ABR", confirmadosTexto = "+20 confirmados",
                local = "Auditório Principal",
                imagemResId = R.drawable.img_evento_cancelado,
                avatar1ResId = R.drawable.img_avatar_1,
                avatar2ResId = R.drawable.img_avatar_2,
                avatar3ResId = R.drawable.img_avatar_3,
                favorito = false,
                dataCompleta = "25 de Abril, 2026", horario = "09:00",
                endereco = "Auditório Principal, UNIFOR",
                organizadorNome = "Lab Criativo", organizadorCargo = "Laboratório",
                organizadorImagemResId = R.drawable.img_avatar_3,
                descricao = "Workshop prático de Design Thinking."
            )
        )

        val rv = findViewById<RecyclerView>(R.id.rvEventos)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = EventoHomeAdapter(
            aoClicarNoEvento = { evento -> DetalhesEventoActivity.abrir(this, evento) },
            aoClicarEmVerDetalhes = { evento -> DetalhesEventoActivity.abrir(this, evento) }
        )
        rv.adapter = adapter
        adapter.submitList(eventos)
    }

    private fun configurarLivros() {
        // 🔧 Substituir por dados reais da API
        val livros = listOf(
            LivroRecomendado("Cultura da Inovação", "Daniel Isenberg", "2021", "023B21", R.drawable.livro_mock),
            LivroRecomendado("Futuro do Trabalho e Educação", "Klaus Schwab", "2022", "034C22", R.drawable.livro_mock),
            LivroRecomendado("Economia Comportamental na era da IA", "Richard Thaler", "2023", "045D23", R.drawable.livro_mock)
        )

        val rv = findViewById<RecyclerView>(R.id.rvLivros)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = LivroRecomendadoAdapter(livros)
    }

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_notifications -> {
                    Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_books -> {
                    startActivity(Intent(this, LivrosReservadosActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}