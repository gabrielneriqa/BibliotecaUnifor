package com.example.uniforeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uniforeventos.model.LivroResponse

class LivroAdapter(
    private val livros: List<LivroResponse>,
    private val aoClicarEmDetalhes: (LivroResponse) -> Unit
) : RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {

    inner class LivroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCapaLivro: ImageView = itemView.findViewById(R.id.imgCapaLivro)
        val tvTituloLivro: TextView = itemView.findViewById(R.id.tvTituloLivro)
        val tvAnoLivro: TextView = itemView.findViewById(R.id.tvAnoLivro)
        val tvAutorLivro: TextView = itemView.findViewById(R.id.tvAutorLivro)
        val tvCodigoLivro: TextView = itemView.findViewById(R.id.tvCodigoLivro)
        val btnVerDetalhes: TextView = itemView.findViewById(R.id.btnVerDetalhes)
        val tvDisponibilidade: TextView = itemView.findViewById(R.id.tvDisponibilidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro, parent, false)

        return LivroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = livros[position]
        val quantidadeDisponivel = livro.quantidadeDisponivel ?: 0

        holder.imgCapaLivro.setImageResource(R.drawable.livro_mock)
        holder.tvTituloLivro.text = livro.titulo
        holder.tvAnoLivro.text = "Ano: ${livro.ano ?: "Não informado"}"
        holder.tvAutorLivro.text = "Autor: ${livro.autor}"
        holder.tvCodigoLivro.text = "Código: ${livro.codigo}"

        holder.tvDisponibilidade.text = if (quantidadeDisponivel > 0) {
            "Disponível"
        } else {
            "Indisponível"
        }

        holder.btnVerDetalhes.setOnClickListener {
            aoClicarEmDetalhes(livro)
        }
    }

    override fun getItemCount(): Int {
        return livros.size
    }
}