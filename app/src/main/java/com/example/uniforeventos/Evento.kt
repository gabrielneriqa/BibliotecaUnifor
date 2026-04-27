package com.example.uniforeventos

data class Evento(
    val id: Long,
    val titulo: String,
    val dia: String,
    val mes: String,
    val confirmadosTexto: String,
    val local: String,
    val imagemResId: Int,
    val avatar1ResId: Int,
    val avatar2ResId: Int,
    val avatar3ResId: Int,
    val favorito: Boolean,
    val dataCompleta: String,
    val horario: String,
    val endereco: String,
    val organizadorNome: String,
    val organizadorCargo: String,
    val organizadorImagemResId: Int,
    val descricao: String
)