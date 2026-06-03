package com.example.uniforeventos.repository

import com.example.uniforeventos.model.LivroResponse
import com.example.uniforeventos.network.RetrofitClient
import retrofit2.Response

class LivroRepository {

    private val livroApi = RetrofitClient.livroApi

    suspend fun listarLivros(): Response<List<LivroResponse>> {
        return livroApi.listarLivros()
    }

    suspend fun buscarLivroPorId(livroId: Long): Response<LivroResponse> {
        return livroApi.buscarLivroPorId(livroId)
    }
}