package org.damc.pruebasemana9.services

object AuthManager {
    private const val AUTH_EMAIL = "karen.morales@uam.edu.ni"
    private const val AUTH_PASSWORD = "karenmorales1"

    suspend fun performAutoLogin(): Boolean {
        return try {
            val response = InternacionalizacionInstance.api.login(
                LoginRequest(
                    email = AUTH_EMAIL,
                    password = AUTH_PASSWORD
                )
            )
            if (response.status == "success") {
                InternacionalizacionInstance.updateToken(response.data.token)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}