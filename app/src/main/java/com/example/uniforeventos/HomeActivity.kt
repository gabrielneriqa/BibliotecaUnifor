package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniforeventos.model.EventoResponse
import com.example.uniforeventos.repository.EventoRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.uniforeventos.model.LivroResponse
import com.example.uniforeventos.repository.LivroRepository
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private var eventos: List<Evento> = emptyList()
    private lateinit var eventoAdapter: EventoHomeAdapter
    private val eventoRepository = EventoRepository()

    private val livroRepository = LivroRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        carregarLivrosDaApi()

        drawerLayout = findViewById(R.id.drawerLayout)

        configurarHamburger()
        configurarSidebar()
        configurarEventos()
        configurarLivros()
        configurarBottomNav()
        configurarFiltros()
        carregarListaEventos()
        configurarVerBilioteca()
        buscarLivro()
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
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        findViewById<View>(R.id.menuLivrosReservados).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, LivrosReservadosActivity::class.java))
        }

        findViewById<View>(R.id.menuEventosReservados).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, MeusEventosConfirmados::class.java)
            intent.putExtra("LISTA_EVENTOS", ArrayList(this.eventos))
            startActivity(intent)
        }

        findViewById<View>(R.id.menuPerfil).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, ContaUsuarioActivity::class.java))
        }
    }

    private fun configurarEventos() {
        val rv = findViewById<RecyclerView>(R.id.rvEventos)

        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        eventoAdapter = EventoHomeAdapter(
            aoClicarNoEvento = { evento -> DetalhesEventoActivity.abrir(this, evento) },
            aoClicarEmVerDetalhes = { evento -> DetalhesEventoActivity.abrir(this, evento) }
        )

        rv.adapter = eventoAdapter

        carregarEventosDaApi()
    }

    private fun carregarEventosDaApi() {
        lifecycleScope.launch {
            try {
                val eventosResponse = eventoRepository.listarEventos()

                val eventosConvertidos = eventosResponse.map { eventoResponse ->
                    eventoResponse.toEvento()
                }

                eventos = eventosConvertidos
                eventoAdapter.submitList(eventosConvertidos)

            } catch (e: Exception) {
                Toast.makeText(
                    this@HomeActivity,
                    "Erro ao carregar eventos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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

    private fun configurarFiltros() {
        val filter = findViewById<View>(R.id.btnFiltros)
        filter.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }
    }

    private fun buscarLivro() {
        findViewById<ImageView>(R.id.ivBuscarLivro).setOnClickListener {
            startActivity(Intent(this, BibliotecaActivity::class.java))
        }
    }

    private fun carregarListaEventos() {
        val botaoListarEventos = findViewById<View>(R.id.tvVerEventos)
        botaoListarEventos?.setOnClickListener {
            val intent = Intent(this, ListaEventosActivity::class.java)
            intent.putExtra("LISTA_EVENTOS", ArrayList(this.eventos))
            startActivity(intent)
        }
    }

    private fun configurarVerBilioteca() {
        val btnBiblioteca = findViewById<TextView>(R.id.tvVerBiblioteca)
        btnBiblioteca.setOnClickListener {
            startActivity(Intent(this, BibliotecaActivity::class.java))
        }
    }

    private fun converterParaLivrosRecomendados(
        livrosResponse: List<LivroResponse>
    ): List<LivroRecomendado> {
        return livrosResponse.map { livroResponse ->
            LivroRecomendado(
                id = livroResponse.id,
                titulo = livroResponse.titulo,
                autor = livroResponse.autor,
                ano = livroResponse.ano ?: "Não informado",
                codigo = livroResponse.codigo,
                imagemResId = R.drawable.livro_mock
            )
        }
    }

    private fun abrirDetalhesLivro(livroId: Long) {
        val intent = Intent(this, DetalhesLivroActivity::class.java)
        intent.putExtra(DetalhesLivroActivity.EXTRA_LIVRO_ID, livroId)
        startActivity(intent)
    }

    private fun configurarLivros() {
        val rv = findViewById<RecyclerView>(R.id.rvLivros)
        rv.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val response = livroRepository.listarLivros()

                if (response.isSuccessful) {
                    val livrosResponse = response.body().orEmpty()
                    val livrosRecomendados = converterParaLivrosRecomendados(livrosResponse)

                    rv.adapter = LivroRecomendadoAdapter(
                        livros = livrosRecomendados,
                        verDetalhes = { livro ->
                            abrirDetalhesLivro(livro.id)
                        }
                    )
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "Não foi possível carregar os livros.",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.e("API_LIVROS", "Erro HTTP: ${response.code()}")
                    Log.e("API_LIVROS", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (exception: Exception) {
                Toast.makeText(
                    this@HomeActivity,
                    "Erro de conexão ao carregar livros.",
                    Toast.LENGTH_SHORT
                ).show()

                Log.e("API_LIVROS", "Erro ao carregar livros", exception)
            }
        }
    }

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true

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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun atualizarLivrosNaTela(livros: List<LivroResponse>) {
        Log.d("API_LIVROS", "Livros recebidos: ${livros.size}")

        livros.forEach { livro ->
            Log.d(
                "API_LIVROS",
                "Livro: ${livro.titulo} | Autor: ${livro.autor}"
            )
        }
    }

    private fun carregarLivrosDaApi() {
        lifecycleScope.launch {
            try {
                val response = livroRepository.listarLivros()

                if (response.isSuccessful) {
                    val livros = response.body().orEmpty()

                    atualizarLivrosNaTela(livros)
                } else {
                    Log.e("API_LIVROS", "Erro HTTP: ${response.code()}")
                    Toast.makeText(
                        this@HomeActivity,
                        "Não foi possível carregar os livros.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (exception: Exception) {
                Log.e("API_LIVROS", "Erro ao carregar livros", exception)

                Toast.makeText(
                    this@HomeActivity,
                    "Erro de conexão ao carregar livros.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}