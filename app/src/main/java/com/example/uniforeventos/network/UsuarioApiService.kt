package com.example.uniforeventos.network

import com.example.uniforeventos.network.dto.LoginRequestDTO
import com.example.uniforeventos.network.dto.TokenUsuarioDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiService {
    @POST("api/usuarios/login/matricula")
    fun loginPorMatricula(@Body request: LoginRequestDTO): Call<TokenUsuarioDTO>
}