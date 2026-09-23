package com.apks.entrevista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import composables.PantallaInicio
import logic.BaseDatos
import logic.CasoViewModel
import logic.DetalleCasoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Prender la base de datos local
        val baseDatos = BaseDatos.obtenerBaseDatos(this)

        // 2. ViewModels
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CasoViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return CasoViewModel(baseDatos.casoDao()) as T
                }
                if (modelClass.isAssignableFrom(DetalleCasoViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return DetalleCasoViewModel(
                        baseDatos.casoDao(),
                        baseDatos.entrevistaDao(),
                        baseDatos.evidenciaDao()
                    ) as T
                }
                throw IllegalArgumentException("ViewModel no reconocido")
            }
        }

        // 3. Instanciamos los ViewModels usando la fábrica de arriba
        val casoViewModel by viewModels<CasoViewModel> { factory }
        val detalleViewModel by viewModels<DetalleCasoViewModel> { factory }

        // 4. El punto de entrada visual de Jetpack Compose
        setContent {
            PantallaInicio(viewModel = casoViewModel)
        }
    }
}