package com.example.uniforeventos.repository

import com.example.uniforeventos.model.EventoPatchRequest
import com.example.uniforeventos.model.EventoRequest
import com.example.uniforeventos.model.EventoResponse
import com.example.uniforeventos.network.RetrofitClient
import retrofit2.Response

class EventoRepository {

    private val eventoApi = RetrofitClient.eventoApi

    suspend fun listarEventos(): List<EventoResponse> {
        return eventoApi.listarEventos()
    }

    suspend fun buscarEventoPorId(id: Long): EventoResponse {
        return eventoApi.buscarEventoPorId(id)
    }

    suspend fun criarEvento(eventoRequest: EventoRequest): EventoResponse {
        return eventoApi.criarEvento(eventoRequest)
    }

    suspend fun atualizarEvento(
        id: Long,
        eventoPatchRequest: EventoPatchRequest
    ): EventoResponse {
        return eventoApi.atualizarEvento(id, eventoPatchRequest)
    }

    suspend fun deletarEvento(id: Long): Response<Unit> {
        return eventoApi.deletarEvento(id)
    }
}