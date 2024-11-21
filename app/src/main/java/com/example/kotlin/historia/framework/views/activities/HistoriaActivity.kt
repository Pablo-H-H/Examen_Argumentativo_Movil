package com.example.kotlin.historia.framework.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.historia.data.repositories.HistoriaRepository
import com.example.kotlin.historia.framework.adapter.HistoriaAdapter
import com.example.kotlin.historia.framework.viewmodel.HistoriaViewModel
import com.example.kotlin.historia.databinding.ActivityHistoriaBinding
import com.example.kotlin.historia.domain.ConsultarHistoriasRequirement
import com.example.kotlin.historia.utils.Historias

class HistoriaActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHistoriaBinding
    private lateinit var viewModel: HistoriaViewModel
    private lateinit var adapter: HistoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Historias = Historias()

        // Inicializar el binding
        initializeBinding()

        // Configurar RecyclerView
        initializeRecyclerView()

        // Crear instancia de Requirement y ViewModel manualmente
        val historiaRepository = HistoriaRepository()
        val historiaRequirement = ConsultarHistoriasRequirement(historiaRepository)
        viewModel = HistoriaViewModel(historiaRequirement)

        // Observar la lista de estilos desde el ViewModel
        observeViewModel()

        // Llamar a la función para cargar los estilos
        viewModel.obtenerHistorias()
    }
    private fun initializeBinding() {
        binding = ActivityHistoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeRecyclerView() {
        adapter = HistoriaAdapter()
        binding.recyclerViewHistoria.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHistoria.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.historias.observe(this) { historia ->
            adapter.submitList(historia)
        }
    }

}