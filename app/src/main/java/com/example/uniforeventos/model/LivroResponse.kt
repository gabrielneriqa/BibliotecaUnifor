package com.example.uniforeventos.model

data class LivroResponse(
    val id: Long,
    val titulo: String,
    val autor: String,
    val ano: String?,
    val codigo: String,
    val imagemUrl: String?,
    val quantidadeTotal: Int?,
    val quantidadeDisponivel: Int?,
    val createdAt: String?,
    val updatedAt: String?
)