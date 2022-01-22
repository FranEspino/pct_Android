package com.example.parquecientificouncp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.parquecientificouncp.components.MyToolbar
import com.example.parquecientificouncp.entities.InvestigacionResponse
import com.example.parquecientificouncp.models.UpdateProgress
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        MyToolbar().showToolbar(this, "Actualizar Progreso", true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        val tv_avance =  findViewById<TextView>(R.id.tv_avance)
        val avance_proyecto =  findViewById<ProgressBar>(R.id.avance_proyecto)
        val btn_progressupdate =  findViewById<Button>(R.id.btn_progressupdate)
        val et_progressinput =  findViewById<EditText>(R.id.et_progressinput)
        val btn_citas = findViewById<Button>(R.id.btn_citas)


        btn_citas.setOnClickListener {
            val intent = Intent(applicationContext, CitasActivity::class.java)
            startActivity(intent)
        }

            val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
            val result : Call<InvestigacionResponse> = userService.getInvestigationInvestigador(UserContextApplication.context.getIdItemInvestigator())
            result.enqueue(object: Callback<InvestigacionResponse> {
                override fun onResponse(
                    call: Call<InvestigacionResponse>,
                    response: Response<InvestigacionResponse>
                ) {
                    val res = response.body()

                    if (res != null) {
                        tv_avance.setText(res.investigacion[0].avance.toString())
                        avance_proyecto.progress = response.body()!!.investigacion[0].avance
                    }
                }
                override fun onFailure(call: Call<InvestigacionResponse>, t: Throwable) {
                }
            })


        btn_progressupdate.setOnClickListener {
            val estado =  et_progressinput.text.toString()

                val newUpdateProgress = UpdateProgress(
                    UserContextApplication.context.getIdItemInvestigation(),
                    "Revisado",
                    estado.toInt()
                )
                updateProgress(newUpdateProgress)


        }


    }

    private fun updateProgress(update_progress: UpdateProgress)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<ResponseBody> = userService.updateProgress(update_progress)
        result.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.body() != null){
                    Toast.makeText(this@ProgressActivity, "Información actualizada con éxito.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }


    private fun getInfoInvestigation(investigationId: Int)  {

    }



}