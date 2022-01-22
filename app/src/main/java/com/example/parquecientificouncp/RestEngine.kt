package com.example.parquecientificouncp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {
    companion object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://3.140.135.200:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        fun<T> buildService(service: Class<T>): T{
            return retrofit.create(service)
        }
    }



}