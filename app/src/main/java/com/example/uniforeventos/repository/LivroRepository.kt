package com.example.uniforeventos.repository

import com.example.uniforeventos.api.RetrofitClient
import com.example.uniforeventos.model.LivroRequest
import com.example.uniforeventos.model.LivroResponse
import retrofit2.Response

class LivroRepository {

    private val livroApi = RetrofitClient.livroApi

    suspend fun listarLivros(): List<LivroResponse> {
        return livroApi.listarLivros()
    }

    suspend fun buscarLivroPorId(id: Long): LivroResponse {
        return livroApi.buscarLivroPorId(id)
    }

    suspend fun criarLivro(livroRequest: LivroRequest): LivroResponse {
        return livroApi.criarLivro(livroRequest)
    }

    suspend fun atualizarLivro(
        id: Long,
        livroRequest: LivroRequest
    ): LivroResponse {
        return livroApi.atualizarLivro(id, livroRequest)
    }

    suspend fun deletarLivro(id: Long): Response<Unit> {
        return livroApi.deletarLivro(id)
    }
}