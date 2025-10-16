package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.LoginRequest
import com.example.mjprestaurant.model.LoginResponse
import com.example.mjprestaurant.model.LogoutRequest
import com.example.mjprestaurant.model.User
import retrofit2.Response
import retrofit2.http.*

interface  ApiService {

    //POST /login -> rep username, password, rol -> retorna token + rol
    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    //POST /logout -> rep token i rol - > torna 200 OK
    @POST("logout")
    suspend fun logout(@Body body: LogoutRequest): Response<Unit>

}