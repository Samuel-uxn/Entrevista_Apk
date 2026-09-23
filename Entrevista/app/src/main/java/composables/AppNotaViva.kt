package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import logic.CasoViewModel
import logic.DetalleCasoViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppNotaViva(
    casoViewModel: CasoViewModel,
    detalleViewModel: DetalleCasoViewModel
) {

    var pantalla by remember {
        mutableStateOf("inicio")
    }

    var casoSeleccionado by remember {
        mutableStateOf(0)
    }

    when (pantalla) {

        "inicio" -> {

            PantallaInicio(
                irACasos = {
                    pantalla = "casos"
                },

                nuevoCaso = {
                    pantalla = "nuevoCaso"
                },

                nuevaEntrevista = {
                    pantalla = "casos"
                },

                estadisticas = {
                    pantalla = "estadisticas"
                }
            )
        }

        "casos" -> {

            PantallaCasos(
                casoViewModel = casoViewModel,

                volverInicio = {
                    pantalla = "inicio"
                },

                nuevoCaso = {
                    pantalla = "nuevoCaso"
                },

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

                volver = {
                    pantalla = "casos"
                },

                nuevaEntrevista = {
                    pantalla = "nuevaEntrevista"
                }
            )
        }

        "nuevoCaso" -> {

            PantallaNuevoCaso(
                casoViewModel = casoViewModel,

                volver = {
                    pantalla = "inicio"
                },

                guardado = {
                    pantalla = "casos"
                }
            )
        }

        "nuevaEntrevista" -> {

            PantallaNuevaEntrevista(
                detalleViewModel = detalleViewModel,
                casoId = casoSeleccionado,

                volver = {
                    pantalla = "detalle"
                }
            )
        }

        "estadisticas" -> {

            PantallaEstadisticas(
                casoViewModel = casoViewModel,

                volver = {
                    pantalla = "inicio"
                }
            )
        }
    }
}

