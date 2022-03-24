package com.example.parquecientificouncp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ComentarioAdapter(val Cometario: ArrayList<Comentarios>): RecyclerView.Adapter<ComentarioAdapter.ComentariosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentariosHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComentariosHolder(layoutInflater.inflate(R.layout.item_comentario,parent, false))
    }

    /*Instancia de nuestro viewHolder, y la posición a rederizar*/
    override fun onBindViewHolder(holder: ComentariosHolder, position: Int) {
        /* El holder puede hacer lo que tenemos en la clase de abajo*/
        holder.render(Cometario[position])
    }

    override fun getItemCount(): Int = Cometario.size
    class  ComentariosHolder(val view: View):RecyclerView.ViewHolder(view) {

        fun render(comentarios: Comentarios){
            view.findViewById<TextView>(R.id.tv_nameuser).text = comentarios.nombres
            view.findViewById<TextView>(R.id.tv_comentario).text = comentarios.comentarios
            Picasso.get().load(comentarios.foto).into(view.findViewById<ImageView>(R.id.iv_investigador))
        }
    }

}
