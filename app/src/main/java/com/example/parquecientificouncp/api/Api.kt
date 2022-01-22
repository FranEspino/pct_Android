package com.example.parquecientificouncp.api

import com.example.parquecientificouncp.entities.LoginResponse
import com.example.parquecientificouncp.entities.PersonaResponse
import retrofit2.Call
import retrofit2.http.*


interface Api {

    @GET("personas")
    fun listUsers(): Call<PersonaResponse>

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("dni") dni:String,
        @Field("clave") clave: String
    ):Call<LoginResponse>
}