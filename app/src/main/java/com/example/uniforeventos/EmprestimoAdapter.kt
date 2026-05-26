package com.example.uniforeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uniforeventos.network.dto.EmprestimoResponseDTO

class EmprestimoAdapter(
    private var emprestimos: List<EmprestimoResponseDTO>,
    private val onEstender: (EmprestimoResponseDTO) -> Unit
) : RecyclerView.Adapter<EmprestimoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloLivroReservado)
        val tvAutor: TextView = view.findViewById(R.id.tvAutorLivroReservado)
        val tvDevolucao: TextView = view.findViewById(R.id.tvDevolucaoLivroReservado)
        val btnEstender: TextView = view.findViewById(R.id.btnEstenderEmprestimoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro_reservado, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emprestimo = emprestimos[position]
        holder.tvTitulo.text = emprestimo.livroTitulo
        holder.tvAutor.text = "Autor: ${emprestimo.livroAutor}"
        holder.tvDevolucao.text = "Devolução: ${formatarData(emprestimo.fimEmprestimo)}"
        holder.btnEstender.setOnClickListener { onEstender(emprestimo) }
    }

    override fun getItemCount(): Int = emprestimos.size

    fun atualizarLista(novaLista: List<EmprestimoResponseDTO>) {
        emprestimos = novaLista
        notifyDataSetChanged()
    }

    // Converte "2026-06-20" → "20/06/2026"
    private fun formatarData(data: String): String {
        return try {
            val partes = data.split("-")
            "${partes[2]}/${partes[1]}/${partes[0]}"
        } catch (e: Exception) {
            data
        }
    }
}