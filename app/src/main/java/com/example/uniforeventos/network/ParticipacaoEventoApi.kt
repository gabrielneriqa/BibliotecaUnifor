package com.example.uniforeventos.network

import com.example.uniforeventos.model.ParticipacaoEventoRequest
import com.example.uniforeventos.model.ParticipacaoEventoResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ParticipacaoEventoApi {

    @POST("api/participacoes-eventos")
    suspend fun confirmarPresenca(
        @Body request: ParticipacaoEventoRequest
    ): Response<ParticipacaoEventoResponse>

    @PATCH("api/participacoes-eventos/cancelar")
    suspend fun cancelarPresenca(
        @Query("usuarioId") usuarioId: Long,
        @Query("eventoId") eventoId: Long
    ): Response<ParticipacaoEventoResponse>

    @GET("api/participacoes-eventos/usuario/{usuarioId}")
    suspend fun listarEventosConfirmadosPorUsuario(
        @Path("usuarioId") usuarioId: Long
    ): Response<List<ParticipacaoEventoResponse>>

    @GET("api/participacoes-eventos/evento/{eventoId}")
    suspend fun listarParticipantesConfirmadosPorEvento(
        @Path("eventoId") eventoId: Long
    ): Response<List<ParticipacaoEventoResponse>>

    @GET("api/participacoes-eventos/{id}")
    suspend fun buscarPorId(
        @Path("id") id: Long
    ): Response<ParticipacaoEventoResponse>
}