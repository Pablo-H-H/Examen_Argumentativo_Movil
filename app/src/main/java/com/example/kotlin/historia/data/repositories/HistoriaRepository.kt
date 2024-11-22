package com.example.kotlin.historia.data.repositories

import android.util.Log
import com.example.kotlin.historia.data.network.model.Historia
import com.parse.ParseCloud
import com.parse.ParseException
import com.parse.ParseObject

class HistoriaRepository {

    // Método para obtener los datos desde la función en la nube de Parse
    fun obtenerHistoria(): List<Historia> {
        val params = hashMapOf<String, Any>()

        return try {
            val result = ParseCloud.callFunction<Map<String, Any>>("hello", params)

            val code = result["code"] as? Int ?: -1
            if (code == 200) {
                @Suppress("UNCHECKED_CAST")
                val dataList = result["data"] as? List<ParseObject> ?: emptyList()


                // Convertimos cada ParseObject a un objeto Historia
                dataList.mapNotNull { parseObject ->
                    try {

                        Historia(
                            date = (parseObject.get("date") ?: "Fecha no disponible").toString(),
                            description = (parseObject.get("description") ?: "Descripción no disponible").toString(),
                            category2 = (parseObject.get("category2") ?: "Categoría no disponible").toString()
                        )
                    } catch (e: Exception) {
                        Log.e("obtenerHistoria", "Error al procesar ParseObject: ${e.message}")
                        null
                    }
                }
            } else {
                Log.e("obtenerHistoria", "Error: Código $code, Mensaje: ${result["message"]}")
                emptyList()
            }
        } catch (e: ParseException) {
            Log.e("obtenerHistoria", "Error de Parse: ${e.message}, Código: ${e.code}")
            emptyList()
        }
    }

    fun filtrarHistoriaAnio(anio: String?): List<Historia> {
        val params = hashMapOf<String, Any>() // Parámetros para la función en la nube
        return try {
            val result = ParseCloud.callFunction<Map<String, Any>>("hello", params)

            val code = result["code"] as? Int ?: -1
            if (code == 200) {
                @Suppress("UNCHECKED_CAST")
                val dataList = result["data"] as? List<ParseObject> ?: emptyList()

                dataList.mapNotNull { parseObject ->
                    try {
                        val date = (parseObject.get("date") ?: "Fecha no disponible").toString()
                        val category2 = (parseObject.get("category2") ?: "Lugar no disponible").toString()

                        // Si el año es nulo o vacío, devuelve todos los resultados
                        if (anio.isNullOrBlank() || date.contains(anio) || category2.contains(anio)) {
                            Historia(
                                date = date,
                                description = (parseObject.get("description") ?: "Descripción no disponible").toString(),
                                category2 = (parseObject.get("category2") ?: "Categoría no disponible").toString()
                            )
                        } else {
                            null // Filtrar historias que no coincidan con el año
                        }
                    } catch (e: Exception) {
                        Log.e("HistoriaRepository", "Error al procesar ParseObject filtrado: ${e.message}")
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
