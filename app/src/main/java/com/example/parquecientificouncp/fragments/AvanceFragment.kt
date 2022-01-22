package com.example.parquecientificouncp.fragments
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parquecientificouncp.*
import com.example.parquecientificouncp.entities.InvestigacionResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AvanceFragment : Fragment() {
    lateinit var result : Call<InvestigacionResponse>
    val arrayListInvestigation = ArrayList<Investigation>()
    val displayListInvestigation = ArrayList<Investigation>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_avance, container, false)
        val id_rol: Int = UserContextApplication.context.getIdRol()
        val ly_containerinvestigador: LinearLayout = view.findViewById(R.id.ly_containerinvestigador)
        val container_rv: LinearLayout = view.findViewById(R.id.container_rv)
        val typeuser  = UserContextApplication.context.getTypeUser()

        if(typeuser == "investigador"){
            container_rv.visibility= View.GONE
            getInformationMatchUser(id_rol, view)
        }

        if(typeuser == "asesor"){
            ly_containerinvestigador.visibility= View.GONE
            getInvestigationsAsesor(UserContextApplication.context.getIdRol(),view)
        }

        return view
    }


    private fun getInvestigationsAsesor(id_asesor: Int, view: View ) {
        arrayListInvestigation.clear()
        displayListInvestigation.clear()
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<InvestigacionResponse> = userService.getInvestigationAsesor(id_asesor)
        result.enqueue(object: Callback<InvestigacionResponse> {
            override fun onResponse(
                call: Call<InvestigacionResponse>,
                response: Response<InvestigacionResponse>
            ) {
                val rv_investigation:RecyclerView = view.findViewById(R.id.rv_investigations)
                val investigation = response.body()

                if (investigation != null) {
                    for(i in 0 until investigation.investigacion.size ){
                        val id =  investigation.investigacion[i].id_investigacion
                        val titulo =  investigation.investigacion[i].titulo
                        val descripcion =  investigation.investigacion[i].descripcion
                        val nombre =   investigation.investigacion[i].nombre
                        val estado =   investigation.investigacion[i].estado
                        val id_rol = investigation.investigacion[i].id_rol
                        var url = "https://www.proturbiomarspa.com/files/_pdf-prueba.pdf"
                        if(investigation.investigacion[i].url_archivo !== null){
                            url = investigation.investigacion[i].url_archivo
                        }
                        arrayListInvestigation.add(Investigation(id,titulo,descripcion,nombre,estado,id_rol,url))
                    }
                    displayListInvestigation.addAll(arrayListInvestigation)
                    val myAdapter = InvestigationAdapter(displayListInvestigation, object: InvestigationAdapter.OnItemClickListener{
                        override fun onItemClick(item: Investigation) {
                            UserContextApplication.context.setIdItemInvestigation(item.id)
                            UserContextApplication.context.setIdItemInvestigator(item.id_rol)
                            UserContextApplication.context.setUtlItemInvestigation(item.url)

                            getInfoInvestigation(item.id)

                        }
                    })

                    rv_investigation.layoutManager = LinearLayoutManager(activity)
                    rv_investigation.adapter = myAdapter

                }
            }

            override fun onFailure(call: Call<InvestigacionResponse>, t: Throwable) {

            }
        })

    }


    private fun getInfoInvestigation(investigationId: Int)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<InvestigacionResponse> = userService.getInvestigationInvestigador(investigationId)
        result.enqueue(object: Callback<InvestigacionResponse> {
            override fun onResponse(
                call: Call<InvestigacionResponse>,
                response: Response<InvestigacionResponse>
            ) {
                val res = response.body()
                if (res != null) {
                        val intent = Intent(activity, FilecontentActivity::class.java)
                        intent.putExtra("archivo",res.investigacion[0].url_archivo)
                        intent.putExtra("id_investigacion",res.investigacion[0].id_investigacion.toString())
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<InvestigacionResponse>, t: Throwable) {
            }
        })
    }


    private fun getInformationMatchUser(id_rol: Int, view: View )  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val typeuser: String = UserContextApplication.context.getTypeUser()
        if(typeuser == "investigador"){
            result =   userService.getInvestigationInvestigador(id_rol)
        }
        if(typeuser == "asesor"){
            result =   userService.getInvestigationAsesor(id_rol)
        }

        result.enqueue(object: Callback<InvestigacionResponse>{
            override fun onResponse(
                call: Call<InvestigacionResponse>,
                response: Response<InvestigacionResponse>
            ) {
                val res = response.body()
                if (res != null) {
                    val base_url: String = getString(R.string.host)

                    val tv_titulo_proyecto: TextView = view.findViewById(R.id.tv_titulo_proyecto)
                    tv_titulo_proyecto.setText(response.body()!!.investigacion[0].titulo)

                    val tv_nombreasesor: TextView = view.findViewById(R.id.tv_nombreasesor)
                    tv_nombreasesor.setText(response.body()!!.investigacion[0].nombre + "\n" + response.body()!!.investigacion[0].apellido )
                    val tv_fecha : TextView = view.findViewById(R.id.fecha_inicio)
                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val output = SimpleDateFormat("dd/MM/yyyy")

                    var d: Date? = null
                    try {
                        d = input.parse(response.body()!!.investigacion[0].fecha_inicio)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    val formatted = output.format(d)

                    tv_fecha.setText("Fecha de creación: "+formatted )

                    val tv_emailasesor : TextView = view.findViewById(R.id.tv_emailasesor)
                    tv_emailasesor.setText(response.body()!!.investigacion[0].correo)

                    val tv_profesionasesor: TextView = view.findViewById(R.id.tv_profesionasesor)
                    tv_profesionasesor.setText(response.body()!!.investigacion[0].telefono)

                    val tv_avance: TextView = view.findViewById(R.id.tv_avance)
                    tv_avance.setText(response.body()!!.investigacion[0].avance.toString() +" %")

                    val avance_proyecto: ProgressBar = view.findViewById(R.id.avance_proyecto)
                    avance_proyecto.progress = response.body()!!.investigacion[0].avance

                    val tv_estadp: TextView = view.findViewById(R.id.tv_estadp)
                    tv_estadp.setText(response.body()!!.investigacion[0].estado)
                    val tv_descripcion : TextView = view.findViewById(R.id.tv_descripcion)
                    tv_descripcion.setText(response.body()!!.investigacion[0].descripcion)

                    val iv_asesor : ImageView= view.findViewById(R.id.iv_asesor)
                    Picasso.get().load(base_url+"/images/"+response.body()!!.investigacion[0].foto).into(iv_asesor);

                    val cv_investigacion : CardView = view.findViewById(R.id.cv_investigacion)

                    cv_investigacion.setOnClickListener {
                        val intent = Intent(activity, FilecontentActivity::class.java)
                        intent.putExtra("archivo",response.body()!!.investigacion[0].url_archivo);
                        startActivity(intent)
                    }

                    val btn_dowloandpdf: Button = view.findViewById(R.id.btn_dowloandpdf)
                    btn_dowloandpdf.setOnClickListener{
                        var request :DownloadManager.Request = DownloadManager.Request(
                            Uri.parse(base_url+"/api/file/documents/"+response.body()!!.investigacion[0].url_archivo))
                            .setTitle(response.body()!!.investigacion[0].titulo)
                            .setDescription(response.body()!!.investigacion[0].descripcion)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setAllowedOverMetered(true)
                        var dm : DownloadManager = activity!!.baseContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                        dm.enqueue(request)
                    }

                    val btn_updatepdf: Button = view.findViewById(R.id.btn_updatepdf)
                    btn_updatepdf.setOnClickListener {
                        val goFileUpdate = Intent(activity, PruebaActivity::class.java)
                        startActivity(goFileUpdate)
                    }
                }
            }

            override fun onFailure(call: Call<InvestigacionResponse>, t: Throwable) {

            }
        })
    }



}