package org.damc.pruebasemana9.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InternacionalizacionInstance {
    private const val BASE_URL = "https://10.0.2.2:8080/api/"

    private var jwtToken: String? = null

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            jwtToken?.let { token ->
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }
        }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: InternacionalizacionApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://localhost:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InternacionalizacionApiService::class.java)
    }

    fun updateToken(token: String) {
        jwtToken = token
    }
}