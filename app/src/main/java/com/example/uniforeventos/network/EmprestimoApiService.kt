package com.example.uniforeventos.network

import com.example.uniforeventos.network.dto.EmprestimoRequestDTO
import com.example.uniforeventos.network.dto.EmprestimoResponseDTO
import com.example.uniforeventos.network.dto.EstenderEmprestimoDTO
import retrofit2.Call
import retrofit2.http.*

interface EmprestimoApiService {
    @POST("api/emprestimos")
    fun criar(@Body request: EmprestimoRequestDTO): Call<EmprestimoResponseDTO>

    @GET("api/emprestimos/usuario/{usuarioId}")
    fun listarPorUsuario(@Path("usuarioId") usuarioId: Long): Call<List<EmprestimoResponseDTO>>

    @GET("api/emprestimos/{id}")
    fun buscarPorId(@Path("id") id: Long): Call<EmprestimoResponseDTO>

    @PATCH("api/emprestimos/{id}/estender")
    fun estender(
        @Path("id") id: Long,
        @Body dto: EstenderEmprestimoDTO
    ): Call<EmprestimoResponseDTO>

    @DELETE("api/emprestimos/{id}")
    fun deletar(@Path("id") id: Long): Call<Void>
}
