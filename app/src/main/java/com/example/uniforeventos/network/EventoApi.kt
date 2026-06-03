package com.example.uniforeventos.network

import com.example.uniforeventos.model.EventoPatchRequest
import com.example.uniforeventos.model.EventoRequest
import com.example.uniforeventos.model.EventoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EventoApi {

    @GET("api/eventos")
    suspend fun listarEventos(): List<EventoResponse>

    @GET("api/eventos/{id}")
    suspend fun buscarEventoPorId(
        @Path("id") id: Long
    ): EventoResponse

    @POST("api/eventos")
    suspend fun criarEvento(
        @Body eventoRequest: EventoRequest
    ): EventoResponse

    @PATCH("api/eventos/{id}")
    suspend fun atualizarEvento(
        @Path("id") id: Long,
        @Body eventoPatchRequest: EventoPatchRequest
    ): EventoResponse

    @DELETE("api/eventos/{id}")
    suspend fun deletarEvento(
        @Path("id") id: Long
    ): Response<Unit>
}