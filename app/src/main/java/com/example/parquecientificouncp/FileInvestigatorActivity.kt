package com.example.parquecientificouncp
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parquecientificouncp.entities.ComentsResponse
import com.example.parquecientificouncp.models.Comment
import com.github.barteksc.pdfviewer.PDFView
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class FileInvestigatorActivity : AppCompatActivity() {
    val arrayList = ArrayList<Comentarios>()
    val displayList = ArrayList<Comentarios>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fileinvestigator)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        val et_comment =  findViewById<EditText>(R.id.et_comment)
        val btn_comment =  findViewById<Button>(R.id.btn_comment)
        val btn_progress =  findViewById<Button>(R.id.btn_progress)
        getComments(UserContextApplication.context.getIdInvestigation())
        btn_progress.visibility = View.GONE


        btn_progress.setOnClickListener {
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent);
        }


        btn_comment.setOnClickListener{
            val comentario = Comment(
                UserContextApplication.context.getIdInvestigation(),
                UserContextApplication.context.getIdPersona(),
                et_comment.text.toString(),
            )
            insertComment(comentario)
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent);
        }


        val progres_bar = findViewById<ProgressBar>(R.id.progressBar)
        progres_bar.visibility = View.VISIBLE

        val pdfView = findViewById<PDFView>(R.id.pdf_viewer)

        if(UserContextApplication.context.getFileUser() != null ){
            FileLoader.with(this)
                .load("http://3.140.135.200:8080/api/file/documents/"+ UserContextApplication.context.getFileUser())
                .fromDirectory("PDFFile", FileLoader.DIR_INTERNAL)
                .asFile(object : FileRequestListener<File> {
                    override fun onLoad(
                        p0: FileLoadRequest?,
                        p1: FileResponse<File>?
                    ) {
                        progres_bar.visibility= View.GONE
                        val pdf_file = p1!!.body.absoluteFile
                        pdfView.fromFile(pdf_file)
                            .password(null)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .enableAnnotationRendering(true)
                            .invalidPageColor(Color.RED)
                            .load()
                    }
                    override fun onError(request: FileLoadRequest?, p1: Throwable?) {
                        FileLoader.with(this@FileInvestigatorActivity)
                            .load("https://www.proturbiomarspa.com/files/_pdf-prueba.pdf")
                            .fromDirectory("PDFFile", FileLoader.DIR_INTERNAL)
                            .asFile(object : FileRequestListener<File> {
                                override fun onLoad(
                                    p0: FileLoadRequest?,
                                    p1: FileResponse<File>?
                                ) {
                                    progres_bar.visibility= View.GONE
                                    val pdf_file = p1!!.body.absoluteFile
                                    pdfView.fromFile(pdf_file)
                                        .password(null)
                                        .enableSwipe(true)
                                        .swipeHorizontal(false)
                                        .enableDoubletap(true)
                                        .enableAnnotationRendering(true)
                                        .invalidPageColor(Color.RED)
                                        .load()
                                }

                                override fun onError(request: FileLoadRequest?, t: Throwable?) {
                                    TODO("Not yet implemented")
                                }
                            })}
                })
        }else{
            FileLoader.with(this)
                .load("https://www.proturbiomarspa.com/files/_pdf-prueba.pdf")
                .fromDirectory("PDFFile", FileLoader.DIR_INTERNAL)
                .asFile(object : FileRequestListener<File> {
                    override fun onLoad(
                        p0: FileLoadRequest?,
                        p1: FileResponse<File>?
                    ) {
                        progres_bar.visibility= View.GONE
                        val pdf_file = p1!!.body.absoluteFile
                        pdfView.fromFile(pdf_file)
                            .password(null)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .enableAnnotationRendering(true)
                            .invalidPageColor(Color.RED)
                            .load()
                    }
                    override fun onError(request: FileLoadRequest?, p1: Throwable?) {
                        Toast.makeText(this@FileInvestigatorActivity, "Error:"+p1!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }




    }

    private fun insertComment(comentario: Comment)  {
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<ResponseBody> = userService.insertComment(comentario)

        result.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("HHHH", response.body().toString())
                if(response.body() != null){
                    Toast.makeText(this@FileInvestigatorActivity, "Comentario registrado", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }



    private fun getComments(id_investigacion: Int)  {
        displayList.clear()
        arrayList.clear()
        val userService: UsersService = RestEngineGet.getRestEngine().create(UsersService::class.java)
        val result : Call<ComentsResponse> = userService.getComments(id_investigacion)
        result.enqueue(object: Callback<ComentsResponse> {
            override fun onResponse(
                call: Call<ComentsResponse>,
                response: Response<ComentsResponse>
            ) {
                val rv_chofer =  findViewById<RecyclerView>(R.id.rv_comentarios)
                val comentarios: ComentsResponse? = response.body()
                if (comentarios != null) {
                    for(i in 0 until comentarios.size ){
                        val nombre =  comentarios[i].nombre+ " " +comentarios[i].apellido
                        val comentario = comentarios[i].comentario
                        var foto = "http://3.140.135.200:8080/images/" + comentarios[i].foto
                        if(comentarios[i].foto === null){
                            foto = "https://res.cloudinary.com/frapoteam/image/upload/v1642693382/user_di2fri.png"
                        }
                        arrayList.add(Comentarios(comentario, nombre,foto))
                    }
                    displayList.addAll(arrayList)
                    val myAdapter = ComentarioAdapter(displayList)
                    rv_chofer.layoutManager = LinearLayoutManager(this@FileInvestigatorActivity)
                    rv_chofer.adapter = myAdapter
                }
            }
            override fun onFailure(call: Call<ComentsResponse>, t: Throwable) {
            }
        })
    }
}