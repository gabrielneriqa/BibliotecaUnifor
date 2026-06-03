package com.example.uniforeventos.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.github.cdimascio.dotenv.dotenv


object RetrofitClient {

    // 🔧 Substituir pela URL pública do backend quando disponível
    // Exemplos:
    //   Emulador local  → "http://10.0.2.2:8080/"
    //   Dispositivo físico na mesma rede → "http://192.168.x.x:8080/"
    //   URL pública (Railway, Render etc.) → "https://sua-url.up.railway.app/"

    val dotenv = dotenv()

    private val baseUrl = dotenv["BASE_URL"]

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val emprestimoApiService: EmprestimoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmprestimoApiService::class.java)
    }

    val participacaoEventoApi: ParticipacaoEventoApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ParticipacaoEventoApi::class.java)
    }

    val livroApi: LivroApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LivroApi::class.java)
    }

    val usuarioApiService: UsuarioApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApiService::class.java)
    }
}