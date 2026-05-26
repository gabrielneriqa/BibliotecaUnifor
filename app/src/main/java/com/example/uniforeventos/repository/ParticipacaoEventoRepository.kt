package com.example.uniforeventos.repository

import com.example.uniforeventos.model.ParticipacaoEventoRequest
import com.example.uniforeventos.model.ParticipacaoEventoResponse
import com.example.uniforeventos.network.RetrofitClient
import retrofit2.Response

class ParticipacaoEventoRepository {

    private val participacaoEventoApi = RetrofitClient.participacaoEventoApi

    suspend fun confirmarPresenca(
        usuarioId: Long,
        eventoId: Long
    ): Response<ParticipacaoEventoResponse> {
        val request = ParticipacaoEventoRequest(
            usuarioId = usuarioId,
            eventoId = eventoId
        )

        return participacaoEventoApi.confirmarPresenca(request)
    }

    suspend fun cancelarPresenca(
        usuarioId: Long,
        eventoId: Long
    ): Response<ParticipacaoEventoResponse> {
        return participacaoEventoApi.cancelarPresenca(
            usuarioId = usuarioId,
            eventoId = eventoId
        )
    }

    suspend fun listarEventosConfirmadosPorUsuario(
        usuarioId: Long
    ): Response<List<ParticipacaoEventoResponse>> {
        return participacaoEventoApi.listarEventosConfirmadosPorUsuario(usuarioId)
    }

    suspend fun listarParticipantesConfirmadosPorEvento(
        eventoId: Long
    ): Response<List<ParticipacaoEventoResponse>> {
        return participacaoEventoApi.listarParticipantesConfirmadosPorEvento(eventoId)
    }

    suspend fun buscarPorId(
        participacaoId: Long
    ): Response<ParticipacaoEventoResponse> {
        return participacaoEventoApi.buscarPorId(participacaoId)
    }
}