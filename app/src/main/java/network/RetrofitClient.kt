package com.example.uniforeventos.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // 🔧 Substituir pela URL pública do backend quando disponível
    // Exemplos:
    //   Emulador local  → "http://10.0.2.2:8080/"
    //   Dispositivo físico na mesma rede → "http://192.168.x.x:8080/"
    //   URL pública (Railway, Render etc.) → "https://sua-url.up.railway.app/"
    private const val BASE_URL = "postgresql://postgres.hbadegkmsykzyxzxigmh:TaZURJ3TJ5Xj3tAx@aws-1-us-east-1.pooler.supabase.com:5432/postgres"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: EmprestimoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmprestimoApiService::class.java)
    }
}