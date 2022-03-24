package com.example.parquecientificouncp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.parquecientificouncp.components.MyToolbar
import com.example.parquecientificouncp.models.ChangePass
import com.example.parquecientificouncp.models.Cite
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        MyToolbar().showToolbar(this, "Actualizar Contraseña", true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        val et_currentpass =  findViewById<EditText>(R.id.et_currentpass)
        val et_newpass1 =  findViewById<EditText>(R.id.et_newpass1)
        val et_newpass2 =  findViewById<EditText>(R.id.et_newpass2)
        val btn_changepass =  findViewById<Button>(R.id.btn_changepass)





        btn_changepass.setOnClickListener {
            if(UserContextApplication.context.getCurrentPass() ==  et_currentpass.text.toString() ){
                if(et_newpass1.text.toString() == et_newpass2.text.toString()){
                        val newPass = ChangePass(
                        UserContextApplication.context.getIdPersona(),
                        et_newpass1.text.toString()
                    )
                    changePassword(newPass)
                    UserContextApplication.context.setChangePass(0)
                    val intent = Intent(this, MenuActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }else{
                    Toast.makeText(this@PasswordActivity, "Asegurese de que las contraseñas coincidan.", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@PasswordActivity, "Contraseña actual invalida!", Toast.LENGTH_SHORT).show()
            }


        }



    }



    private fun changePassword(pass: ChangePass)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<ResponseBody> = userService.changePass(pass)
        result.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("Responseeee", response.body().toString())
                if(response.body() != null){
                    Toast.makeText(this@PasswordActivity, "Clave modificada!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }
}