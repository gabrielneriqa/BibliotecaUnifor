package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniforeventos.model.EventoResponse
import com.example.uniforeventos.repository.EventoRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class ListaEventosActivity : AppCompatActivity() {

    private lateinit var recyclerViewEventos: RecyclerView
    private lateinit var eventoAdapter: EventoHomeAdapter
    private val eventoRepository = EventoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_eventos)

        inicializarViews()
        configurarRecyclerView()
        carregarEventosDaApi()
        carregarFiltros()
        configurarBottomNav()
    }

    private fun inicializarViews() {
        recyclerViewEventos = findViewById(R.id.rvEventos)

        findViewById<TextView>(R.id.tvVoltar).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.ivMenu).setOnClickListener {
            finish()
        }
    }

    private fun configurarRecyclerView() {
        eventoAdapter = EventoHomeAdapter(
            aoClicarNoEvento = { eventoSelecionado ->
                DetalhesEventoActivity.abrir(this, eventoSelecionado)
            },
            aoClicarEmVerDetalhes = { eventoSelecionado ->
                DetalhesEventoActivity.abrir(this, eventoSelecionado)
            }
        )

        recyclerViewEventos.apply {
            layoutManager = LinearLayoutManager(this@ListaEventosActivity)
            adapter = eventoAdapter
            setHasFixedSize(true)
        }
    }

    private fun carregarEventosDaApi() {
        lifecycleScope.launch {
            try {
                val eventosResponse = eventoRepository.listarEventos()

                val eventosConvertidos = eventosResponse.map { eventoResponse ->
                    eventoResponse.toEvento()
                }

                carregarEventos(eventosConvertidos)

            } catch (e: Exception) {
                Toast.makeText(
                    this@ListaEventosActivity,
                    "Erro ao carregar eventos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun carregarEventos(eventos: List<Evento>) {
        eventoAdapter.submitList(eventos)
    }

    private fun EventoResponse.toEvento(): Evento {
        val data = runCatching {
            LocalDate.parse(this.dataInicio)
        }.getOrNull()

        val diaFormatado = data
            ?.dayOfMonth
            ?.toString()
            ?.padStart(2, '0')
            ?: "--"

        val mesFormatado = data
            ?.month
            ?.getDisplayName(TextStyle.SHORT, Locale("pt", "BR"))
            ?.replace(".", "")
            ?.uppercase(Locale("pt", "BR"))
            ?: "---"

        val dataCompletaFormatada = data?.format(
            DateTimeFormatter.ofPattern("dd 'de' MMMM, yyyy", Locale("pt", "BR"))
        ) ?: this.dataInicio

        return Evento(
            id = this.id,
            titulo = this.titulo,
            dia = diaFormatado,
            mes = mesFormatado,
            confirmadosTexto = "+0 confirmados",
            local = this.local ?: "Local não informado",
            imagemResId = R.drawable.img_evento_placeholder,
            avatar1ResId = R.drawable.img_avatar_1,
            avatar2ResId = R.drawable.img_avatar_2,
            avatar3ResId = R.drawable.img_avatar_3,
            favorito = false,
            dataCompleta = dataCompletaFormatada,
            horario = this.horario,
            endereco = this.endereco ?: "Endereço não informado",
            organizadorNome = "UNIFOR",
            organizadorCargo = "Organizador",
            organizadorImagemResId = R.drawable.img_avatar_1,
            descricao = this.descricao ?: ""
        )
    }

    private fun carregarFiltros() {
        findViewById<LinearLayout>(R.id.llVerFiltrosEventos).setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
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