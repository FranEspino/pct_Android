package com.example.parquecientificouncp;

import com.example.parquecientificouncp.entities.ResponseUpdatePdf;
import com.example.parquecientificouncp.entities.UpdateFoto;
import com.example.parquecientificouncp.models.UpdatePdf;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @Headers("Content-Type: application/json")
    @PUT("investigacion/archivo64/{id_investigacion}")
    Call<ResponseBody> uploadDocument(
            @Path("id_investigacion") Integer id_investigador,
            @Body UpdatePdf updatePdf
    );

    @Headers("Content-Type: application/json")
    @PUT("usuarios/foto")
    Call<ResponseBody> uploadFoto(
            @Body UpdateFoto updateFoto
    );

}
