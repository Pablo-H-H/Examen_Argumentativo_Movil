package com.example.kotlin.historia.data.network

import android.content.Context
import android.util.Log
import com.example.kotlin.historia.utils.Constants.APPLICATION_ID
import com.example.kotlin.historia.utils.Constants.BASE_URL
import com.example.kotlin.historia.utils.Constants.CLIENT_KEY
import com.parse.Parse
import com.parse.ParseCloud
import com.parse.ParseException

/**
 * Módulo de inyección de dependencias responsable de inicializar Parse y
 * manejar las llamadas a las funciones en la nube de Parse.
 */
object NetworkModuleDI {
    /**
     * Inicializa Parse con la configuración necesaria para la conexión al backend.
     *
     * @param context El contexto de la aplicación utilizado para la configuración de Parse.
     */
    fun initializeParse(context: Context) {
        try {
            // Configuración de Parse
            Parse.initialize(
                Parse.Configuration
                    .Builder(context)
                    .applicationId(APPLICATION_ID)
                    .clientKey(CLIENT_KEY)
                    .server(BASE_URL)
                    .build(),
            )


            Log.d("Parse", "SDK de Parse inicializado correctamente.")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Base Parse", "Error al inicializar Parse: ${e.message}")
        }
    }


    /**
     * Llama a una función en la nube de Parse de forma asíncrona y maneja el resultado o error.
     *
     * @param T El tipo de resultado esperado de la función en la nube.
     * @param nombreFuncion El nombre de la función en la nube que se va a ejecutar.
     * @param parametros Un mapa de parámetros que se pasarán a la función en la nube.
     * @param callback La función que se llamará con el resultado o el error. Si la llamada es exitosa,
     * [callback] recibirá el resultado como el primer argumento y `null` como segundo argumento.
     * En caso de error, recibirá `null` como primer argumento y una excepción como segundo argumento.
     */
    fun <T> callCloudFunction(
        nombreFuncion: String,
        parametros: HashMap<String, Any>,
        callback: (T?, Exception?) -> Unit,
    ) {
        ParseCloud.callFunctionInBackground(nombreFuncion, parametros) { resultado: T?, e: ParseException? ->
            if (e == null) {
                callback(resultado, null)
                Log.e("Base Parse", "resultado: $resultado")
            } else {
                callback(null, e)
                Log.e("Base Parse", "Error: ${e.message}")
            }
        }
    }
}