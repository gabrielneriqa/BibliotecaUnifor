package com.example.uniforeventos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaEventosActivity : AppCompatActivity() {

    private lateinit var recyclerViewEventos: RecyclerView
    private lateinit var eventoAdapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_eventos)

        inicializarViews()
        configurarRecyclerView()

        // Aqui você passa sua lista real
        // carregarEventos(eventosRecebidos)
    }

    private fun inicializarViews() {
        recyclerViewEventos = findViewById(R.id.rvEventos)
    }

    private fun configurarRecyclerView() {
        eventoAdapter = EventoAdapter(
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
}