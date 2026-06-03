package com.example.uniforeventos.api

import com.example.uniforeventos.model.LivroRequest
import com.example.uniforeventos.model.LivroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface LivroApi {

    @GET("api/livros")
    suspend fun listarLivros(): List<LivroResponse>

    @GET("api/livros/{id}")
    suspend fun buscarLivroPorId(
        @Path("id") id: Long
    ): LivroResponse

    @POST("api/livros")
    suspend fun criarLivro(
        @Body livroRequest: LivroRequest
    ): LivroResponse

    @PATCH("api/livros/{id}")
    suspend fun atualizarLivro(
        @Path("id") id: Long,
        @Body livroRequest: LivroRequest
    ): LivroResponse

    @DELETE("api/livros/{id}")
    suspend fun deletarLivro(
        @Path("id") id: Long
    ): Response<Unit>
}