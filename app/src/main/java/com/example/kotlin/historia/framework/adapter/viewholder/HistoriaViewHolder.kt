package com.example.kotlin.historia.framework.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.historia.R
import com.example.kotlin.historia.data.network.model.Historia

class HistoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val fechaTextView: TextView = itemView.findViewById(R.id.Fecha)
    private val descripcionTextView: TextView = itemView.findViewById(R.id.Descripcion)

    fun bind(historia: Historia) {
        fechaTextView.text = historia.date
        descripcionTextView.text = historia.description
    }
}