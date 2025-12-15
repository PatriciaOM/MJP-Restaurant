package com.example.mjprestaurant.network
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Objecte singleton que proporciona la instància configurada a Retrofit per a trucades HTTPS.
 *
 * Aquesta versió inclou una configuració especial "Unsafe" per permetre connexions
 * HTTPS amb certificats autofirmats (típic en entorns de desenvolupament local).
 *
 * @author Martin Muñoz Pozuelo
 */
object RetrofitInstance {

    /**
     * URL base del servidor per emulador d'Android amb HTTPS.
     * El servidor utilitza el port 8080 segons l'application.properties.
     */
    private const val BASE_URL = "https://10.0.2.2:8080/"

    /**
     * Client HTTP insegur (NOMÉS PER DESENVOLUPAMENT).
     * Ignora la validació de certificats SSL per poder connectar amb localhost.
     */
    private fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Crea un trust manager que no valida les cadenes de certificats
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Instal·la el trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true } // Accepta qualsevol hostname

            // Afegim el logging
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(logging)

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * Instancia lazy d'ApiService configurada amb Retrofit i HTTPS insegur.
     */
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient()) // Usem el client especial
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}