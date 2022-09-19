package com.example.jobreadinesschallenge.api

import com.example.jobreadinesschallenge.infra.ProjectConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    // O RetrofitClient é criado como companion object para ser tratado como um Singleton.
    // Ou seja, assim só podemos instancia-lo uma única vez

    companion object {

        private lateinit var instance: Retrofit
        // Já inicializo o token como string vazia, para ele nunca ser nulo
        private var token : String = ""

        private fun getRetrofitInstance(): Retrofit {
            // O okhttp é a classe que faz a conexão com a internet
            val httpClient = OkHttpClient.Builder()

            // Incluimos um Interceptor para incluir o token de acesso ao Header
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer ${ProjectConstants.RETROFIT.TOKEN}")
                        .build()

                    return chain.proceed(request)
                }
            })

            // Verificando se foi inicializado
            if (!Companion::instance.isInitialized) {
                instance = Retrofit.Builder()
                    .baseUrl(ProjectConstants.RETROFIT.BASE_URL)
                    .client(httpClient.build()) //chamada a internet
                    .addConverterFactory(GsonConverterFactory.create()) //converte o Json
                    .build()

            }

            return instance
        }

        // Com essa função geral, conseguimos criar diversos serviços apenas alterando o parametro
        fun <T> createService(service: Class<T>): T {
            return getRetrofitInstance().create(service)
        }
    }

}