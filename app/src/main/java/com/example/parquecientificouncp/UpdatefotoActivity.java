package com.example.parquecientificouncp;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parquecientificouncp.components.MyToolbar;
import com.example.parquecientificouncp.entities.Foto;
import com.example.parquecientificouncp.entities.UpdateFoto;
import com.example.parquecientificouncp.models.UpdatePdf;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatefotoActivity extends AppCompatActivity {
    private int REQ_PDF = 21;
    private  String encodePDF;
    private static int RESULT_LOAD_IMAGE = 1;
    String sImage;
    String contenidoImagen = "";
    ImageView ivFotoUsuario;

    public static final int RESULT_GALLERY = 0;
    ImageView imageView;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefoto);
        MyToolbar toolbar  = new MyToolbar();
        toolbar.showToolbar(this,"Cambiar foto",true);
        ivFotoUsuario = (ImageView) findViewById(R.id.iv_user);
        Picasso.get().load(UserContextApplication.context.getFotoUser()).into(ivFotoUsuario);

        Button btn_updatefoto = (Button) findViewById(R.id.btn_updatefoto);

        btn_updatefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                if (ContextCompat.checkSelfPermission(UpdatefotoActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    // when permission is nor granted
                    // request permission
                    ActivityCompat.requestPermissions(UpdatefotoActivity.this
                            , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

                }
                else
                {
                    // when permission
                    // is granted
                    // create method
                    imageChooser();
                }

            }
        });
    }

    void imageChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione Imagen"), 5675);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5675 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                contenidoImagen = getStringImage(bitmap);
                Picasso.get().load(getImageUri(this, bitmap)).rotate(270).into(ivFotoUsuario);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 25, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Foto foto = new Foto(
                encodedImage,"png"
        );

        UpdateFoto updateFoto = new UpdateFoto(foto,UserContextApplication.context.getIdPersona());
        updateFoto(updateFoto);
        return encodedImage;
    }

    private void updateFoto(UpdateFoto updateFoto) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getAPI().uploadFoto(updateFoto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("RES IMAGHE 64", response.body().toString());
                if(response.isSuccessful()){
                    Toast.makeText(UpdatefotoActivity.this, "Archivo Actualizado Correctamente", Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(UpdatefotoActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdatefotoActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

}