package composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import logic.DetalleCasoViewModel

import androidx.compose.ui.tooling.preview.Preview


@Composable
fun PantallaDetalleCaso(
    detalleViewModel: DetalleCasoViewModel,
    volver: () -> Unit,
    nuevaEntrevista: () -> Unit
) {

    val caso by detalleViewModel.casoActual.collectAsState()
    val entrevistas by detalleViewModel.entrevistas.collectAsState()
    val evidencias by detalleViewModel.evidencias.collectAsState()

    var pestaña by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
    ) {

        // Barra superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF182838))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "‹",
                color = Color.White,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "▤  NotaViva",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (caso != null) {

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = caso!!.titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${caso!!.fecha}     ${caso!!.estado}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                TextoPestana(
                    texto = "Resumen",
                    seleccionada = pestaña == 0
                ) {
                    pestaña = 0
                }

                TextoPestana(
                    texto = "Entrevistas",
                    seleccionada = pestaña == 1
                ) {
                    pestaña = 1
                }

                TextoPestana(
                    texto = "Conclusiones",
                    seleccionada = pestaña == 2
                ) {
                    pestaña = 2
                }

                TextoPestana(
                    texto = "Evidencias",
                    seleccionada = pestaña == 3
                ) {
                    pestaña = 3
                }
            }

            when (pestaña) {

                0 -> {

                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {

                        Text(
                            text = "Resumen",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Text(
                                text = caso!!.descripcion,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                1 -> {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Entrevistas (${entrevistas.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Button(
                                onClick = nuevaEntrevista
                            ) {

                                Text("+ Nueva")
                            }
                        }

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(entrevistas) { entrevista ->

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ) {

                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = "●",
                                            fontSize = 28.sp,
                                            color = Color.Gray
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Column {

                                            Text(
                                                text = entrevista.nombre,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Text(
                                                text = entrevista.rol,
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )

                                            Text(
                                                text = entrevista.fecha,
                                                fontSize = 11.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {

                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {

                        Text(
                            text = "Conclusiones",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Text(
                                text = caso!!.conclusion
                                    ?: "Todavia no hay una conclusion.",
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                3 -> {

                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {

                        Text(
                            text = "Evidencias (${evidencias.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        evidencias.forEach { evidencia ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {

                                Row(
                                    modifier = Modifier.padding(12.dp)
                                ) {

                                    Text(
                                        text = "▣",
                                        fontSize = 25.sp
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column {

                                        Text(
                                            text = evidencia.nombreArchivo,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = "${evidencia.tipo} - ${evidencia.tamano}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextoPestana(
    texto: String,
    seleccionada: Boolean,
    accion: () -> Unit
) {

    TextButton(
        onClick = accion
    ) {

        Text(
            text = texto,
            color = if (seleccionada) {
                Color(0xFF1266D6)
            } else {
                Color.Gray
            },
            fontSize = 11.sp
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun PantallaEstadisticasPreview() {
    PantallaEstadisticas(
        casoViewModel = CasoViewModel(CasoDaoFalso()),
        volver = {}
    )
}
