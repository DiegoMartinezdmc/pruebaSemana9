package org.damc.pruebasemana9.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InternacionalizacionApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("")
    suspend fun getFaculties(): List<Faculty>
}

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val status: String, val message: String, val data: TokenData)

data class TokenData(val token: String)