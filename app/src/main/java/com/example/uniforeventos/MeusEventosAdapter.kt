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

class MeusEventosAdapter (
    private val aoClicarNoEvento: () -> Unit,
    private val aoClicarEmVerDetalhes: () -> Unit
) : ListAdapter<Evento, MeusEventosAdapter.MeusEventosViewHolder>(MeusEventosDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeusEventosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meu_evento, parent, false)
        return MeusEventosViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeusEventosViewHolder, position: Int) {
        holder.vincular(getItem(position))
    }

    inner class MeusEventosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagemEvento: ImageView           = itemView.findViewById(R.id.ivEvento)
        private val textoDia: TextView                = itemView.findViewById(R.id.tvDia)
        private val textoMes: TextView                = itemView.findViewById(R.id.tvMes)
        private val cardFavorito: MaterialCardView    = itemView.findViewById(R.id.cardFavorito)
        private val textoTitulo: TextView             = itemView.findViewById(R.id.tvTituloEvento)
        private val imagemParticipante1: ImageView    = itemView.findViewById(R.id.ivParticipantes1)
        private val imagemParticipante2: ImageView    = itemView.findViewById(R.id.ivParticipantes2)
        private val imagemParticipante3: ImageView    = itemView.findViewById(R.id.ivParticipantes3)
        private val textoConfirmados: TextView        = itemView.findViewById(R.id.tvConfirmados)
        private val textoLocal: TextView              = itemView.findViewById(R.id.tvLocal)
        private val botaoVerDetalhes: MaterialCardView = itemView.findViewById(R.id.btnEditarEvento)

        fun vincular(evento: Evento) {
            imagemEvento.setImageResource(evento.imagemResId)
            textoDia.text = evento.dia
            textoMes.text = evento.mes
            textoTitulo.text = evento.titulo
            imagemParticipante1.setImageResource(evento.avatar1ResId)
            imagemParticipante2.setImageResource(evento.avatar2ResId)
            imagemParticipante3.setImageResource(evento.avatar3ResId)
            textoConfirmados.text = evento.confirmadosTexto
            textoLocal.text = evento.local
            cardFavorito.visibility = if (evento.favorito) View.VISIBLE else View.GONE
            itemView.setOnClickListener { aoClicarNoEvento() }
            botaoVerDetalhes.setOnClickListener { aoClicarEmVerDetalhes() }
        }
    }
}

class MeusEventosDiffCallback : DiffUtil.ItemCallback<Evento>() {
    override fun areItemsTheSame(oldItem: Evento, newItem: Evento) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Evento, newItem: Evento) = oldItem == newItem
}