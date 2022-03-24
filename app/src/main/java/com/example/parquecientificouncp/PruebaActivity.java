package com.example.parquecientificouncp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parquecientificouncp.components.MyToolbar;
import com.example.parquecientificouncp.entities.ResponseUpdatePdf;
import com.example.parquecientificouncp.models.UpdatePdf;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PruebaActivity extends AppCompatActivity {

    private int REQ_PDF = 21;
    private  String encodePDF;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        MyToolbar myToolbar = new MyToolbar();
        myToolbar.showToolbar(this, "Actualizar Pdf", true);

        Button btn_updatepdf = (Button) findViewById(R.id.btn_updatepdf2);
        btn_updatepdf.setOnClickListener( v -> {
            Intent chosseFile =  new Intent(Intent.ACTION_GET_CONTENT);
            chosseFile.setType("application/pdf");
            chosseFile = Intent.createChooser(chosseFile, "Choose a file pdf");
            startActivityForResult(Intent.createChooser(chosseFile,"Selecciona un archivo"),REQ_PDF);
        } );
    }
    private void uploadDocument(UpdatePdf updatePdf) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getAPI().uploadDocument( UserContextApplication.context.getIdInvestigation(),updatePdf);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if(response.isSuccessful()){
                  Toast.makeText(PruebaActivity.this, "Archivo Actualizado Correctamente", Toast.LENGTH_LONG).show();
                  Intent intent =  new Intent(PruebaActivity.this, MenuActivity.class);
                  startActivity(intent);
              }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PruebaActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==REQ_PDF && resultCode== Activity.RESULT_OK && data!=null){
                Uri path = data.getData();
                try {
                    InputStream inputStream = PruebaActivity.this.getContentResolver().openInputStream(path);
                    byte [] pdfInBytes = new byte[inputStream.available()];
                    inputStream.read(pdfInBytes);
                    encodePDF = Base64.encodeToString(pdfInBytes,Base64.DEFAULT);
                    UpdatePdf updatePdf = new UpdatePdf(encodePDF);
                    uploadDocument(updatePdf);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }






}