package com.example.uniforeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class EventoAdapter(
    private val aoClicarNoEvento: (Evento) -> Unit,
    private val aoClicarEmVerDetalhes: (Evento) -> Unit
) : ListAdapter<Evento, EventoAdapter.EventoViewHolder>(EventoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evento, parent, false)

        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = getItem(position)
        holder.vincular(evento)
    }

    inner class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagemEvento: ImageView = itemView.findViewById(R.id.ivEvento)
        private val textoDia: TextView = itemView.findViewById(R.id.tvDia)
        private val textoMes: TextView = itemView.findViewById(R.id.tvMes)
        private val cardFavorito: MaterialCardView = itemView.findViewById(R.id.cardFavorito)
        private val textoTituloEvento: TextView = itemView.findViewById(R.id.tvTituloEvento)
        private val imagemParticipante1: ImageView = itemView.findViewById(R.id.ivParticipantes1)
        private val imagemParticipante2: ImageView = itemView.findViewById(R.id.ivParticipantes2)
        private val imagemParticipante3: ImageView = itemView.findViewById(R.id.ivParticipantes3)
        private val textoConfirmados: TextView = itemView.findViewById(R.id.tvConfirmados)
        private val textoLocal: TextView = itemView.findViewById(R.id.tvLocal)
        private val botaoVerDetalhes: MaterialCardView = itemView.findViewById(R.id.btnVerDetalhes)

        fun vincular(evento: Evento) {
            imagemEvento.setImageResource(evento.imagemResId)
            textoDia.text = evento.dia
            textoMes.text = evento.mes
            textoTituloEvento.text = evento.titulo
            imagemParticipante1.setImageResource(evento.avatar1ResId)
            imagemParticipante2.setImageResource(evento.avatar2ResId)
            imagemParticipante3.setImageResource(evento.avatar3ResId)
            textoConfirmados.text = evento.confirmadosTexto
            textoLocal.text = evento.local

            cardFavorito.visibility = if (evento.favorito) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                aoClicarNoEvento(evento)
            }

            botaoVerDetalhes.setOnClickListener {
                aoClicarEmVerDetalhes(evento)
            }
        }
    }
}

class EventoDiffCallback : DiffUtil.ItemCallback<Evento>() {
    override fun areItemsTheSame(oldItem: Evento, newItem: Evento): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Evento, newItem: Evento): Boolean {
        return oldItem == newItem
    }
}