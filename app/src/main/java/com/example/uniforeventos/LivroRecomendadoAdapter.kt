package com.example.uniforeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LivroRecomendadoAdapter(
    private val livros: List<LivroRecomendado>
) : RecyclerView.Adapter<LivroRecomendadoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCapa: ImageView      = view.findViewById(R.id.ivCapaLivro)
        val tvTitulo: TextView     = view.findViewById(R.id.tvTituloLivro)
        val tvAutor: TextView      = view.findViewById(R.id.tvAutorLivro)
        val tvAno: TextView        = view.findViewById(R.id.tvAnoLivro)
        val tvCodigo: TextView     = view.findViewById(R.id.tvCodigoLivro)
        val tvVerDetalhes: TextView = view.findViewById(R.id.tvVerDetalhes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro_recomendado, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val livro = livros[position]
        holder.ivCapa.setImageResource(livro.imagemResId)
        holder.tvTitulo.text = livro.titulo
        holder.tvAutor.text = "Autor: ${livro.autor}"
        holder.tvAno.text = "Ano: ${livro.ano}"
        holder.tvCodigo.text = "Código: ${livro.codigo}"
        holder.tvVerDetalhes.setOnClickListener { /* TODO: abrir detalhes do livro */ }
    }

    override fun getItemCount(): Int = livros.size
}