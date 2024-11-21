package com.example.kotlin.historia.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.historia.data.network.model.Historia
import com.example.kotlin.historia.domain.ConsultarHistoriasRequirement
import kotlinx.coroutines.launch


class HistoriaViewModel(private val historiaRequirement: ConsultarHistoriasRequirement) : ViewModel() {

    private val _historias = MutableLiveData<List<Historia>>()
    val historias: LiveData<List<Historia>> get() = _historias

    fun obtenerHistorias() {
        viewModelScope.launch {
            val listaHistorias = historiaRequirement.obtenerHistoria()
            _historias.postValue(listaHistorias)
        }
    }
}