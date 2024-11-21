package com.example.kotlin.historia.data.repositories

import android.util.Log
import com.example.kotlin.historia.data.network.model.Historia
import com.parse.ParseCloud
import com.parse.ParseException
import com.parse.ParseObject

class HistoriaRepository {

    // Método para obtener los datos desde la función en la nube de Parse
    fun obtenerHistoria(page: Int): List<Historia> {
        val params = hashMapOf<String, Any>(
            "page" to page
        )

        return try {
            val result = ParseCloud.callFunction<Map<String, Any>>("hello", params)

            val code = result["code"] as? Int ?: -1
            if (code == 200) {
                @Suppress("UNCHECKED_CAST")
                val dataList = result["data"] as? List<ParseObject> ?: emptyList()

                Log.d("HistoriaRepository", "Datos recibidos: $dataList")

                // Convertimos cada ParseObject a un objeto Historia
                dataList.mapNotNull { parseObject ->
                    try {
                        Log.d("HistoriaRepository", "Procesando objeto: ${parseObject.objectId}")

                        Historia(
                            date = (parseObject.get("date") ?: "Fecha no disponible").toString(),
                            description = (parseObject.get("description") ?: "Descripción no disponible").toString(),
                            category2 = (parseObject.get("category2") ?: "Categoría no disponible").toString()
                        )
                    } catch (e: Exception) {
                        Log.e("HistoriaRepository", "Error al procesar ParseObject: ${e.message}")
                        null
                    }
                }
            } else {
                Log.e("HistoriaRepository", "Error: Código $code, Mensaje: ${result["message"]}")
                emptyList()
            }
        } catch (e: ParseException) {
            Log.e("HistoriaRepository", "Error de Parse: ${e.message}, Código: ${e.code}")
            emptyList()
        }
    }
}
