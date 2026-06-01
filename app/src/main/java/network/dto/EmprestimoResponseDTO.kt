package com.example.uniforeventos.network.dto

data class EmprestimoResponseDTO(
    val id: Long,
    val usuarioId: Long,
    val usuarioNome: String,
    val livroId: Long,
    val livroTitulo: String,
    val livroAutor: String,
    val inicioEmprestimo: String,   // "YYYY-MM-DD"
    val fimEmprestimo: String       // "YYYY-MM-DD"
)