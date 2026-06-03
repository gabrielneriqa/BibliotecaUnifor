package com.example.uniforeventos.network.dto

data class EmprestimoRequestDTO(
    val usuarioId: Long,
    val livroId: Long,
    val fimEmprestimo: String? = null   // "YYYY-MM-DD" — null = prazo padrão (14 dias)
)