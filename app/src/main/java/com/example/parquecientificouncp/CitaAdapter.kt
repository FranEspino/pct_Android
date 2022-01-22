package com.example.parquecientificouncp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitaAdapter(val Citas: List<Citas>): RecyclerView.Adapter<CitaAdapter.CitasHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CitasHolder(layoutInflater.inflate(R.layout.item_cita,parent, false))
    }

    /*Instancia de nuestro viewHolder, y la posición a rederizar*/
    override fun onBindViewHolder(holder: CitasHolder, position: Int) {
        /* El holder puede hacer lo que tenemos en la clase de abajo*/
        holder.render(Citas[position])
    }

    override fun getItemCount(): Int = Citas.size

    class  CitasHolder(val view: View):RecyclerView.ViewHolder(view) {

        fun render(citas: Citas){
            view.findViewById<TextView>(R.id.tv_titlecite).text = citas.titulo
            view.findViewById<TextView>(R.id.tv_description).text = citas.descripcion
            view.findViewById<TextView>(R.id.tv_linkreunion).text = citas.link

            view.findViewById<TextView>(R.id.tv_hora).text = citas.hora
            view.findViewById<TextView>(R.id.tv_fecha).text = citas.fecha

        }
    }



}