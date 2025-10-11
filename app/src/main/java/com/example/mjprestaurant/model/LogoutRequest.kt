package com.example.mjprestaurant.model

data class LogoutRequest(
    val token: String,
    val role: String
)