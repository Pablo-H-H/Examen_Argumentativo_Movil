package com.example.kotlin.historia.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.kotlin.historia.R
import com.example.kotlin.historia.data.network.model.Historia
import com.example.kotlin.historia.framework.adapter.viewholder.HistoriaViewHolder

class HistoriaAdapter : ListAdapter<Historia, HistoriaViewHolder>(HistoriaDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historias, parent, false)
        return HistoriaViewHolder(view)
    }
    override fun onBindViewHolder(holder: HistoriaViewHolder, position: Int) {
        val historia = getItem(position)
        holder.bind(historia)

        holder.itemView.setOnClickListener{

        }
    }

    class HistoriaDiffCallback : DiffUtil.ItemCallback<Historia>() {
        override fun areItemsTheSame(oldItem: Historia, newItem: Historia): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Historia, newItem: Historia): Boolean {
            return oldItem == newItem
        }
    }

}