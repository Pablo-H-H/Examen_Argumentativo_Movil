package com.example.kotlin.historia.domain

import android.util.Log
import com.example.kotlin.historia.data.network.model.Historia
import com.example.kotlin.historia.data.repositories.HistoriaRepository

class ConsultarHistoriasRequirement(private val repository: HistoriaRepository) {
    fun obtenerHistoria(): List<Historia>{
        Log.e("repository", repository.obtenerHistoria().toString(), )
        return repository.obtenerHistoria()
    }
    fun filtrarHistoriaAnio(int: String): List<Historia>{
        Log.e("repository", repository.obtenerHistoria().toString(), )
        return repository.filtrarHistoriaAnio(int)
    }
}