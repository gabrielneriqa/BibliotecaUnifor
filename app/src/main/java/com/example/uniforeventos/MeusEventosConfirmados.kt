package com.example.uniforeventos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MeusEventosConfirmados : AppCompatActivity() {

    private lateinit var recyclerViewEventosConfirmados: RecyclerView
    private lateinit var recyclerViewMeusEventos: RecyclerView
    private lateinit var meusEventosAdapter: MeusEventosAdapter
    private lateinit var eventosConfirmadosAdapter: EventoHomeAdapter
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meus_eventos_confirmados)

        drawerLayout = findViewById(R.id.drawerLayout)

        val eventosRecebidos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("LISTA_EVENTOS", ArrayList::class.java) as? ArrayList<Evento>
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("LISTA_EVENTOS") as? ArrayList<Evento>
        }

        inicializarViews()
        configurarRecyclerView()

        eventosRecebidos?.let {
            carregarEventos(it)
        }

        configurarNavegacao()
        configurarBackPress()
        configurarSidebar()
    }

    private fun inicializarViews() {
        recyclerViewEventosConfirmados = findViewById(R.id.rvEventosConfirmados)
        recyclerViewMeusEventos = findViewById(R.id.rvMeusEventos)
    }
    private fun configurarRecyclerView() {
        eventosConfirmadosAdapter = EventoHomeAdapter(
            aoClicarNoEvento = { eventoSelecionado ->
                DetalhesEventoConfirmado.abrir(this, eventoSelecionado)
            },
            aoClicarEmVerDetalhes = { eventoSelecionado ->
                DetalhesEventoConfirmado.abrir(this, eventoSelecionado)
            }
        )

        recyclerViewEventosConfirmados.apply {
            layoutManager = LinearLayoutManager(this@MeusEventosConfirmados)
            adapter = eventosConfirmadosAdapter
            setHasFixedSize(true)
        }


        meusEventosAdapter = MeusEventosAdapter(
            aoClicarNoEvento = {
                startActivity(Intent(this, EditarEventoActivity::class.java))
            },
            aoClicarEmVerDetalhes = {
                startActivity(Intent(this, EditarEventoActivity::class.java))
            }
        )

        recyclerViewMeusEventos.apply {
            layoutManager = LinearLayoutManager(this@MeusEventosConfirmados)
            adapter = meusEventosAdapter
            setHasFixedSize(true)
        }
    }

    fun carregarEventos(eventos: List<Evento>) {
        eventosConfirmadosAdapter.submitList(eventos)
        meusEventosAdapter.submitList(eventos)
    }

    private fun configurarNavegacao() {
        // Botão Voltar
        findViewById<TextView>(R.id.tvVoltar).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Menu Lateral
        findViewById<View>(R.id.ivHamburger).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Barra Inferior (Padrão HomeActivity)
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
        }
        findViewById<View>(R.id.menuPerfil).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, ContaUsuarioActivity::class.java))
        }
    }


    private fun configurarBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}