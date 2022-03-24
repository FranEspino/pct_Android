package com.example.parquecientificouncp


import com.example.parquecientificouncp.entities.*
import com.example.parquecientificouncp.entities.LoginResponse
import com.example.parquecientificouncp.models.*
import com.example.parquecientificouncp.models.Cite
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface UsersService {
    @GET("personas")
    fun listUsers(): Call<PersonaResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(@Body login: Login): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("comentario")
    fun insertComment(@Body comentario: Comment): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("cita")
    fun insertCite(@Body cite: Cite): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("cambiarclave")
    fun changePass(@Body cite: ChangePass): Call<ResponseBody>



    @Headers("Content-Type: application/json")
    @PUT("usuarios")
    fun modifyUserInformation(@Body modifyindo: ModifyInfo): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @PUT("investigacion/detalle")
    fun updateProgress(@Body modifyindo: UpdateProgress): Call<ResponseBody>

    @GET("investigacion?")
    fun getInvestigationInvestigador(@Query("id_investigador") id_investigador: Int): Call<InvestigacionResponse>

    @GET("investigacion?")
    fun getInvestigationAsesor(@Query("id_asesor") id_asesor: Int): Call<InvestigacionResponse>

    @GET("comentario?")
    fun getComments(@Query("id_investigacion") id_investigacion: Int): Call<ComentsResponse>

    @GET("cita?")
    fun getCites(@Query("id_investigacion") id_investigacion: Int): Call<CitesResponse>



}