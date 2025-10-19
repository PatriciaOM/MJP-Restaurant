package com.example.mjprestaurant.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objecte singleton que proporciona la instància configurada a Retrofrit per a trucades HTTP
 *
 * Aquesta classe s'encarrega de:
 *  - Configurar l'URL base del servidor.
 *  - Afegir interceptos per loggin.
 *  - Crear i mantenir la instància única de Retrofit.
 *  - Configurar el convertidor Gson per a JSON.
 *
 *  @see ApiService
 *  @see AuthRepository
 *
 *  @author Martin Muñoz Pozuelo
 */
object RetrofitInstance{

    /**
     * URL base del servidor per emulador d'Android.
     * 10.0.2.2 es l'alias de localhost a l'emulador Android.
     */
    private const val BASE_URL = "http://10.0.2.2:8080/"

    /**
     * Interceptor de logging amb depuració de peticions HTTP.
     * Nivel body mostra headers, body del request i respons.
     */
    private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * Client HTTP configurat com a interceptor del logging.
     */
     private val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    /**
     * Instancia lazy d'ApiService configurada amb Retrofit.
     *
     * S'inicia només quan s'accedeix per primera vegada.
     *
     * @return Instancia d'ApiService per fer trucades a l'API
     */
        val api: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
