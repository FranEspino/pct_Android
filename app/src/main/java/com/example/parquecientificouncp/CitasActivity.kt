package com.example.parquecientificouncp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parquecientificouncp.components.MyToolbar
import com.example.parquecientificouncp.entities.CitesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitasActivity : AppCompatActivity() {
    val arrayListCitas = ArrayList<Citas>()
    val displayListCita = ArrayList<Citas>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)
        MyToolbar().showToolbar(this, "Citas", true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        val btn_newcite =  findViewById<Button>(R.id.btn_newcite)
        btn_newcite.setOnClickListener {
            val intent = Intent(this, CitaActivity::class.java)

            startActivity(intent);
        }
        val typeUser = UserContextApplication.context.getTypeUser()

        if(typeUser == "asesor"){
            Log.d("€€€€€€€IDDDD",UserContextApplication.context.getIdItemInvestigation().toString())
            getCitas(UserContextApplication.context.getIdItemInvestigation())

        }

        if(typeUser == "investigador"){
            getCitas(UserContextApplication.context.getIdInvestigation())

        }





    }

    private fun getCitas(id_investigacion: Int)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<CitesResponse> = userService.getCites(id_investigacion)
        result.enqueue(object: Callback<CitesResponse> {
            override fun onResponse(
                call: Call<CitesResponse>,
                response: Response<CitesResponse>
            ) {
                val rv_citas =  findViewById<RecyclerView>(R.id.rv_citas)
                val citas = response.body()
                if (citas != null) {
                    for(i in 0 until citas.cites.size ){
                        val titulo =  citas.cites[i].titulo
                        val descripcion =  citas.cites[i].descripcion
                        val link =  citas.cites[i].link
                        val hora =  citas.cites[i].hora
                        val fecha =  citas.cites[i].fecha
                        arrayListCitas.add(Citas(titulo,descripcion,link,fecha,hora))
                    }

                    displayListCita.addAll(arrayListCitas)

                    val myAdapter = CitaAdapter(displayListCita)
                    rv_citas.layoutManager = LinearLayoutManager(this@CitasActivity)
                    rv_citas.adapter = myAdapter
                }
            }
            override fun onFailure(call: Call<CitesResponse>, t: Throwable) {
            }
        })
    }
}