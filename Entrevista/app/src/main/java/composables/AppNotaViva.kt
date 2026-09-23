package composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import logic.CasoViewModel
import logic.DetalleCasoViewModel
import com.apks.entrevista.ui.theme.FondoOscuro

@Composable
fun AppNotaViva(
    casoViewModel: CasoViewModel,
    detalleViewModel: DetalleCasoViewModel
) {
    var pantalla by remember { mutableStateOf("inicio") }
    var casoSeleccionado by remember { mutableStateOf(0) }

    BackHandler(enabled = pantalla != "inicio") {
        pantalla = when (pantalla) {
            "casos" -> "inicio"
            "detalle" -> "casos"
            "nuevoCaso" -> "inicio"
            "editarCaso" -> "detalle"
            "nuevaEntrevista" -> "detalle"
            "estadisticas" -> "inicio"
            else -> "inicio"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .systemBarsPadding()
    ) {
        when (pantalla) {
            "inicio" -> {
                PantallaInicio(
                    irACasos = { pantalla = "casos" },
                    nuevoCaso = { pantalla = "nuevoCaso" },
                    nuevaEntrevista = { pantalla = "casos" },
                    estadisticas = { pantalla = "estadisticas" }
                )
            }

            "casos" -> {
                PantallaCasos(
                    casoViewModel = casoViewModel,
                    volverInicio = { pantalla = "inicio" },
                    nuevoCaso = { pantalla = "nuevoCaso" },
                    abrirCaso = { id ->
                        casoSeleccionado = id
                        detalleViewModel.cargarDetallesDelCaso(id)
                        pantalla = "detalle"
                    }
                )
            }

            "detalle" -> {
                PantallaDetalleCaso(
                    detalleViewModel = detalleViewModel,
                    volver = { pantalla = "casos" },
                    nuevaEntrevista = { pantalla = "nuevaEntrevista" },
                    editarCaso = { pantalla = "editarCaso" },
                    casoEliminado = { pantalla = "casos" }
                )
            }

            "editarCaso" -> {
                PantallaEditarCaso(
                    detalleViewModel = detalleViewModel,
                    volver = { pantalla = "detalle" },
                    guardado = { pantalla = "detalle" }
                )
            }

            "nuevoCaso" -> {
                PantallaNuevoCaso(
                    casoViewModel = casoViewModel,
                    volver = { pantalla = "inicio" },
                    guardado = { pantalla = "casos" }
                )
            }

            "nuevaEntrevista" -> {
                PantallaNuevaEntrevista(
                    detalleViewModel = detalleViewModel,
                    casoId = casoSeleccionado,
                    volver = { pantalla = "detalle" }
                )
            }

            "estadisticas" -> {
                PantallaEstadisticas(
                    casoViewModel = casoViewModel,
                    volver = { pantalla = "inicio" }
                )
            }
        }
    }
}