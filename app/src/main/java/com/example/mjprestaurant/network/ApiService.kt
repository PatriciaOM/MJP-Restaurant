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

    //De moment l'usuari no necessita consultar el perfil ni canviar dades
    /*@GET("user")
    suspend fun getAllUsers(@Header("token") token: String): Response<List<User>>

    @GET("user")
    suspend fun getUser(
        @Query("user") username: String,
        @Header("token") token: String
    ): Response<User>


    */
    // En teoria no els necessitem des de l'usuari
    /* @PUT("user")
    suspend fun createUser(@Body body: Map<String, String>): Response<Unit>

    @DELETE("user")
    suspend fun deleteUser(
        @Query("username") username: String,
        @Header("token") token: String
    ): Response <Unit>
    */
}