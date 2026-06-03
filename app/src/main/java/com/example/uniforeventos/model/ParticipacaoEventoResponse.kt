package com.example.uniforeventos.model

data class ParticipacaoEventoResponse(
    val id: Long,
    val usuarioId: Long,
    val usuarioNome: String,
    val eventoId: Long,
    val eventoNome: String,
    val eventoDescricao: String,
    val eventoDataInicio: String,
    val eventoDataFim: String,
    val eventoLocal: String,
    val eventoCategoria: String,
    val eventoVagas: Int,
    val status: String,
    val dataConfirmacao: String,
    val dataCancelamento: String?
)