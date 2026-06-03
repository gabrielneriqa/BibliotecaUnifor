package com.example.uniforeventos.model

data class EventoResponse(
    val id: Long,
    val titulo: String,
    val descricao: String?,
    val dataInicio: String,
    val horario: String,
    val local: String?,
    val endereco: String?,
    val imagemUrl: String?,
    val organizadorId: String?,
    val criadoPor: String?,
    val status: String,
    val createdAt: String?,
    val updatedAt: String?
)