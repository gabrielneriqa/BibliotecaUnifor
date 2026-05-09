package com.example.uniforeventos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaEventosActivity : AppCompatActivity() {

    private lateinit var recyclerViewEventos: RecyclerView
    private lateinit var eventoAdapter: EventoHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_eventos)

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

        carregarFiltros()
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

    fun carregarEventos(eventos: List<Evento>) {
        eventoAdapter.submitList(eventos)
    }

    private fun carregarFiltros(){
        findViewById<LinearLayout>(R.id.llVerFiltrosEventos).setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }
    }
}