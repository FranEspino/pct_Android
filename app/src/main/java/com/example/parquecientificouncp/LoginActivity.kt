package com.example.parquecientificouncp
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.parquecientificouncp.entities.LoginResponse
import com.example.parquecientificouncp.models.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)
        val dniUser= findViewById<EditText>(R.id.dni_user)
        val claveUser= findViewById<EditText>(R.id.clave_user)
        val btn_login = findViewById<Button>(R.id.btn_login)

        val link_portal = findViewById<TextView>(R.id.tv_linkportal)
        link_portal.setOnClickListener{
            val i =  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://3.140.135.200/portalpct/"));
            startActivity(i);

        }
        link_portal.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(link_portal, Linkify.ALL);


        btn_login.setOnClickListener {
            val dni = dniUser.text.toString().trim()
            val clave = claveUser.text.toString().trim()
            if(dni.isEmpty()){
                dniUser.error = "El email es obligatorio."
                dniUser.requestFocus()
                return@setOnClickListener
            }

            if(clave.isEmpty()){
                claveUser.error = "La clave es obligatoria."
                claveUser.requestFocus()
                return@setOnClickListener
            }
            val info = Login(dni,clave)
            LoginUser(info)
        }

    }

    fun LoginUser(userData: Login){
        val retrofit = RestEngine.buildService(UsersService::class.java)
        retrofit.loginUser(userData).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, error: Throwable) {
                    Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse( call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val res = response.body()
                    if (res != null) {
                        val base_url: String = getString(R.string.host)
                        UserContextApplication.context.setLogin(true)
                        UserContextApplication.context.setIdRol(res.rol.id_rol)
                        UserContextApplication.context.setIdPersona(res.persona[0].id_persona)
                        UserContextApplication.context.setTypeUser(res.rol.tipo)
                        UserContextApplication.context.setNameUser(res.persona[0].nombre)
                        UserContextApplication.context.setLastNameUser(res.persona[0].apellido)
                        if(res.persona[0].foto === null){
                            UserContextApplication.context.setFotoUser("https://res.cloudinary.com/frapoteam/image/upload/v1642693382/user_di2fri.png")
                        }else{
                            UserContextApplication.context.setFotoUser(base_url+"/images/"+res.persona[0].foto)

                        }
                        UserContextApplication.context.setPhoneUser(res.persona[0].telefono)
                        UserContextApplication.context.setEmailUser(res.persona[0].correo)
                        UserContextApplication.context.setAddressUser(res.persona[0].direccion)

                        val intent = Intent(applicationContext, MenuActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@LoginActivity, "Usuario o contraseña no valida.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        if(UserContextApplication.context.isLogin()){
            val intent = Intent(applicationContext, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
