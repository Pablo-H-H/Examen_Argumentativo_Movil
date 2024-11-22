package com.example.kotlin.historia.framework.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.historia.data.repositories.HistoriaRepository
import com.example.kotlin.historia.databinding.ActivityHistoriaBinding
import com.example.kotlin.historia.domain.ConsultarHistoriasRequirement
import com.example.kotlin.historia.framework.adapter.HistoriaAdapter
import com.example.kotlin.historia.framework.viewmodel.HistoriaViewModel
import com.example.kotlin.historia.utils.Historias

class HistoriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoriaBinding
    private lateinit var viewModel: HistoriaViewModel
    private lateinit var adapter: HistoriaAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

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

        // Configurar el debounce para el EditText
        setupDebounceFilter()
    }

    private fun initializeBinding() {
        binding = ActivityHistoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeRecyclerView() {
        adapter = HistoriaAdapter()
        binding.recyclerViewHistoria.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHistoria.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.historias.observe(this) { historia ->
            adapter.submitList(historia)
        }
    }

    private fun setupDebounceFilter() {
        binding.fechafiltro.addTextChangedListener { editable ->
            searchRunnable?.let { handler.removeCallbacks(it) } // Eliminar cualquier ejecución previa

            searchRunnable = Runnable {
                val query = editable.toString()
                viewModel.filtrarHistoriaAnio(query)
            }

            handler.postDelayed(searchRunnable!!, 400) // Ejecutar después de 400ms
        }

    }
}
