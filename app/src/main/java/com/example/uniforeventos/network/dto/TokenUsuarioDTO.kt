package com.example.uniforeventos.network.dto

data class TokenUsuarioDTO(
    val usuarioId: Long,
    val usuarioNome: String,
    val token: String,
    val expires: String
)