package com.example.parquecientificouncp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.parquecientificouncp.components.MyToolbar
import com.example.parquecientificouncp.models.Comment
import com.example.parquecientificouncp.models.ModifyInfo
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        MyToolbar().showToolbar(this, "Modificar información", true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)
        val et_nombre =  findViewById<EditText>(R.id.et_nombre)
        val et_apellido =  findViewById<EditText>(R.id.et_apellido)
        val et_telefono =  findViewById<EditText>(R.id.et_telefono)
        val et_correo =  findViewById<EditText>(R.id.et_correo)
        val et_direccion =  findViewById<EditText>(R.id.et_direccion)
        val btn_insertcite = findViewById<Button>(R.id.btn_insertcite)

        et_nombre.setText(UserContextApplication.context.getNameUser())
        et_apellido.setText(UserContextApplication.context.getLastNameUser())
        et_telefono.setText(UserContextApplication.context.getPhoneUser())
        et_correo.setText(UserContextApplication.context.getEmailUser())
        et_direccion.setText(UserContextApplication.context.getAddressUser())

        btn_insertcite.setOnClickListener{

            val newInfor = ModifyInfo(
                UserContextApplication.context.getIdPersona(),
                et_nombre.text.toString(),
                et_apellido.text.toString(),
                et_telefono.text.toString(),
                et_correo.text.toString(),
                et_direccion.text.toString()
            )
            UserContextApplication.context.setNameUser(et_nombre.text.toString())
            UserContextApplication.context.setLastNameUser(et_apellido.text.toString())
            UserContextApplication.context.setPhoneUser(et_telefono.text.toString())
            UserContextApplication.context.setEmailUser(et_correo.text.toString())
            UserContextApplication.context.setAddressUser(et_direccion.text.toString())

            modifyInformation(newInfor)

        }
    }

    private fun modifyInformation(modifyindo: ModifyInfo)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<ResponseBody> = userService.modifyUserInformation(modifyindo)
        result.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.body() != null){
                    Toast.makeText(this@ProfileActivity, "Información actualizada con éxito.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }


}