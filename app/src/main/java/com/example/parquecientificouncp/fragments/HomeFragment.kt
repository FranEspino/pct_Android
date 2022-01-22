package com.example.parquecientificouncp.fragments
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.parquecientificouncp.*
import com.example.parquecientificouncp.entities.InvestigacionResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    @SuppressLint("LongLogTag")
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_home, container, false)

        val name_user:TextView = view.findViewById(R.id.name_user)
        val iv_investigador:ImageView = view.findViewById(R.id.iv_investigador)
        Picasso
            .get()
            .load(UserContextApplication.context.getFotoUser())
            .fit()
            .centerInside()
            .placeholder(R.drawable.progress_animation)
            .into(iv_investigador)

        name_user.setText(UserContextApplication.context.getNameUser() + "\n" +
                          UserContextApplication.context.getLastNameUser())
        val btn_citas: CardView= view.findViewById(R.id.cv_citas)
        val typeuser  = UserContextApplication.context.getTypeUser()
        if(typeuser == "asesor"){
            btn_citas.visibility =  View.GONE

        }


        btn_citas.setOnClickListener {
            val intent = Intent(activity, CitasActivity::class.java)
            startActivity(intent)
        }
        getInfoInvestigation(UserContextApplication.context.getIdRol(),view)


        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://diariocorreo.pe/resizer/82N4hFiR76qA8SuWGiqhmT01ufw=/580x330/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/UNL76A6GW5HDTFXD4YPY2EGWKU.jpeg", " Licenciada por Sunedu"

        ))
        imageList.add(SlideModel("https://larepublica.pe/resizer/drvOCuXvTSJmbbVizNG5lwFzR-k=/1250x735/top/smart/arc-anglerfish-arc2-prod-gruporepublica.s3.amazonaws.com/public/Q5OZMB2OQFGGFJ7QSIYAQ3ICIE.png", " Laboratorios modernos"
            ))
        imageList.add(SlideModel("https://diariocorreo.pe/resizer/4Ga03OExWCIW_CzejqnRaf0ZzDI=/580x330/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/4UN6MKQXINB67FFT36TY7ZGURI.jpg"," Parque científico tecnológico"
            ))


        val imageSlider: ImageSlider = view.findViewById(R.id.slide_image)
        imageSlider.setImageList(imageList)
        return view
    }

    private fun getInfoInvestigation(investigationId: Int, view: View )  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<InvestigacionResponse> = userService.getInvestigationInvestigador(investigationId)
        result.enqueue(object: Callback<InvestigacionResponse> {
            override fun onResponse(
                call: Call<InvestigacionResponse>,
                response: Response<InvestigacionResponse>
            ) {
                Log.d("HHHH",response.body()!!.investigacion.toString())


                if( response.body()!!.investigacion.isEmpty()){
                    Toast.makeText(getActivity(),"No tienes una investigacion, consulta al portal!",
                        Toast.LENGTH_LONG).show();
                    val intent = Intent(activity, LoginActivity::class.java)
                    UserContextApplication.context.setLogin(false)
                    startActivity(intent)
                }else{
                    if(response.isSuccessful){


                        val btn_project: CardView= view.findViewById(R.id.cv_proyecto)
                        val typeuser  = UserContextApplication.context.getTypeUser()
                        if(typeuser == "investigador"){

                            UserContextApplication.context.setIdInvestigation(response.body()!!.investigacion[0].id_investigacion)

                            if(response.body()!!.investigacion[0].url_archivo != null){
                                UserContextApplication.context.setFileUser(response.body()!!.investigacion[0].url_archivo)
                            }

                            btn_project.setOnClickListener {
                                val intent = Intent(activity, FileInvestigatorActivity::class.java)
                                intent.putExtra("archivo",response.body()!!.investigacion[0].url_archivo);
                                startActivity(intent)
                            }
                        }else{
                            btn_project.visibility =  View.GONE
                        }

                    }else{
                        Toast.makeText(getActivity(),"No tienes una investigacion, consulta al portal!",
                            Toast.LENGTH_LONG).show();
                        UserContextApplication.context.setLogin(false)
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }


            }
            override fun onFailure(call: Call<InvestigacionResponse>, t: Throwable) {
            }
        })
    }


}

