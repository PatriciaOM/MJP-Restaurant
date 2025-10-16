package com.example.mjprestaurant.model

 data class LoginRequest(
        val username: String,
        val password: String,
        val role: String = "user"
    )
