package com.example.parquecientificouncp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.parquecientificouncp.components.MyToolbar
import com.example.parquecientificouncp.models.Cite
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CitaActivity : AppCompatActivity() , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var btn_timePicker: Button
    private lateinit var btn_horaPicker: Button
    var id_investogation: Int = 0

    private lateinit var et_date: EditText
    private lateinit var et_time: EditText
    var dia = 0
    var mes = 0
    var anio = 0
    var hora = 0
    var minutos = 0

    var guardarDia = 0
    var guardarMes = 0
    var guardaranio = 0
    var guardarHora = 0
    var guardarMinutos = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita)
        MyToolbar().showToolbar(this, "Nueva Cita", true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)


        btn_timePicker = findViewById<Button>(R.id.btn_fecha)
        btn_horaPicker = findViewById<Button>(R.id.btn_hora)
        et_date = findViewById<EditText>(R.id.et_fecha)
        et_time =findViewById<EditText>(R.id.et_hora)
        pickDate();
        val et_title = findViewById<EditText>(R.id.et_title)
        val et_description = findViewById<EditText>(R.id.et_description)
        val et_link = findViewById<EditText>(R.id.et_link)
        et_link.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(et_link, Linkify.ALL);
        val et_fecha = findViewById<EditText>(R.id.et_fecha)
        val et_hora = findViewById<EditText>(R.id.et_hora)


        val btn_insertcite = findViewById<Button>(R.id.btn_insertcite)

        val typeUser = UserContextApplication.context.getTypeUser()

        if(typeUser == "asesor"){
            id_investogation = UserContextApplication.context.getIdItemInvestigation()

        }

        if(typeUser == "investigador"){
            id_investogation = UserContextApplication.context.getIdInvestigation()

        }


        btn_insertcite.setOnClickListener{
            val newCite = Cite(
                id_investogation,
                et_title.text.toString(),
                et_description.text.toString(),
                et_fecha.text.toString(),
                et_hora.text.toString(),
                et_link.text.toString(),
            )
            insertCite(newCite)
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent);
        }

    }
    private fun insertCite(cite: Cite)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<ResponseBody> = userService.insertCite(cite)
        result.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.body() != null){
                    Toast.makeText(this@CitaActivity, "Cita registrado!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    private fun getDateCalendar(){
        val cal: Calendar = Calendar.getInstance()
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal[Calendar.MONTH]

        anio = cal.get(Calendar.YEAR)
    }

    private fun getDateTimer(){
        val cal: Calendar = Calendar.getInstance()
        hora = cal.get(Calendar.HOUR)
        minutos = cal.get(Calendar.MINUTE)
    }



    private fun pickDate(){
        btn_timePicker.setOnClickListener{
            getDateCalendar()
            DatePickerDialog(this,R.style.date_picker,this,anio,mes,dia).show()
        }
        btn_horaPicker.setOnClickListener{
            getDateTimer()
            TimePickerDialog(this,R.style.TimePicker, this,hora,minutos,true).show();
        }


    }
    override fun onDateSet(view: DatePicker?, anio: Int, mes: Int, dia: Int) {
        guardarDia = dia
        guardarMes =mes +1
        guardaranio = anio
        getDateCalendar()
        et_date.setText("$guardarDia/$guardarMes/$guardaranio")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        guardarHora = hourOfDay
        guardarMinutos = minute
        getDateTimer()
        et_time.setText("$guardarHora:$guardarMinutos")
    }

}