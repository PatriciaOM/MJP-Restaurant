package com.example.mjprestaurant.network

import android.util.Log
import com.example.mjprestaurant.model.LoginRequest
import com.example.mjprestaurant.model.LoginResponse
import com.example.mjprestaurant.model.LogoutRequest
import retrofit2.Response


class AuthRepository {

    suspend fun login(username: String, password: String, role: String): Response<LoginResponse> {
        val request = LoginRequest(username, password, role)
        return RetrofitInstance.api.login(request)
    }

    suspend fun logout(token: String): Response<Unit> {
        val request = LogoutRequest(sessionToken = token)
        return RetrofitInstance.api.logout(request)
    }
}