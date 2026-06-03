package com.example.uniforeventos.network

import com.example.uniforeventos.model.LivroResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LivroApi {

    @GET("api/livros")
    suspend fun listarLivros(): Response<List<LivroResponse>>

    @GET("api/livros/{id}")
    suspend fun buscarLivroPorId(
        @Path("id") livroId: Long
    ): Response<LivroResponse>
}