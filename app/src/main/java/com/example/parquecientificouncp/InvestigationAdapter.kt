package com.example.parquecientificouncp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class InvestigationAdapter(val Investigation: List<Investigation> , param: OnItemClickListener): RecyclerView.Adapter<InvestigationAdapter.InvestigationsHolder>() {
      var items = Investigation
      var listener = param

    interface OnItemClickListener {
        fun onItemClick(item: Investigation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestigationsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvestigationsHolder(layoutInflater.inflate(R.layout.item_investigation,parent, false))
    }

    override fun onBindViewHolder(holder: InvestigationsHolder, position: Int) {
        holder.render(items.get(position), listener )
    }

    override fun getItemCount(): Int = Investigation.size

    class  InvestigationsHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun render(investigation: Investigation,  listener: OnItemClickListener ){
            val btn_item = view.findViewById<Button>(R.id.btn_getCurrentId)

            btn_item.setOnClickListener (
                object : View.OnClickListener{
                    override fun onClick(v: View?) {
                            listener.onItemClick(investigation)
                    }}
            )

            view.findViewById<TextView>(R.id.tv_titleinvestigation).text = investigation.titulo
            view.findViewById<TextView>(R.id.tv_descinvestigation).text = investigation.descripcion
            view.findViewById<TextView>(R.id.tv_personinvestigation).text = investigation.nombre
            view.findViewById<TextView>(R.id.tv_stateinvestigation).text = investigation.estado
        }
    }





}


