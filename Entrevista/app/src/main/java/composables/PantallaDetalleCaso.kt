package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun PantallaDetalleCaso(
    detalleViewModel: DetalleCasoViewModel,
    volver: () -> Unit,
    nuevaEntrevista: () -> Unit,
    editarCaso: () -> Unit,
    casoEliminado: () -> Unit
) {

    val caso by detalleViewModel.casoActual.collectAsState()
    val entrevistas by detalleViewModel.entrevistas.collectAsState()
    val evidencias by detalleViewModel.evidencias.collectAsState()

    var pestaña by remember { mutableStateOf(0) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarDialogoEvidencia by remember { mutableStateOf(false) }

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
                fontSize = 32.sp,
                modifier = Modifier.clickable { volver() }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "▤  NotaViva",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "✎",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable { editarCaso() }
                    .padding(6.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "🗑",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable { mostrarDialogoEliminar = true }
                    .padding(6.dp)
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
                ) { pestaña = 0 }

                TextoPestana(
                    texto = "Entrevistas",
                    seleccionada = pestaña == 1
                ) { pestaña = 1 }

                TextoPestana(
                    texto = "Conclusiones",
                    seleccionada = pestaña == 2
                ) { pestaña = 2 }

                TextoPestana(
                    texto = "Evidencias",
                    seleccionada = pestaña == 3
                ) { pestaña = 3 }
            }

            when (pestaña) {

                0 -> {
                    Column(modifier = Modifier.padding(14.dp)) {

                        Text(
                            text = "Resumen",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = caso!!.descripcion,
                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Estado del caso",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("En investigacion", "Publicado", "Cerrada").forEach { opcion ->
                                val seleccionado = caso!!.estado == opcion
                                Button(
                                    onClick = { detalleViewModel.cambiarEstado(caso!!.id, opcion) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (seleccionado) Color(0xFF1266D6) else Color(0xFFE0E0E0),
                                        contentColor = if (seleccionado) Color.White else Color.Black
                                    ),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(opcion, fontSize = 11.sp)
                                }
                            }
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
                            Button(onClick = nuevaEntrevista) {
                                Text("+ Nueva")
                            }
                        }

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(entrevistas) { entrevista ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "●", fontSize = 28.sp, color = Color.Gray)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(text = entrevista.nombre, fontWeight = FontWeight.Bold)
                                            Text(text = entrevista.rol, fontSize = 12.sp, color = Color.Gray)
                                            Text(text = entrevista.fecha, fontSize = 11.sp, color = Color.Gray)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    var conclusionTexto by remember(caso!!.id) {
                        mutableStateOf(caso!!.conclusion ?: "")
                    }

                    Column(modifier = Modifier.padding(14.dp)) {

                        Text(
                            text = "Conclusiones",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = conclusionTexto,
                            onValueChange = { conclusionTexto = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            placeholder = { Text("Escribe la conclusion del caso...") }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                detalleViewModel.guardarConclusionYCerrar(caso!!.id, conclusionTexto)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar conclusion y cerrar caso")
                        }
                    }
                }

                3 -> {
                    Column(modifier = Modifier.padding(14.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Evidencias (${evidencias.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = { mostrarDialogoEvidencia = true }) {
                                Text("+ Agregar")
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        evidencias.forEach { evidencia ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Row(modifier = Modifier.padding(12.dp)) {
                                    Text(text = "▣", fontSize = 25.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = evidencia.nombreArchivo, fontWeight = FontWeight.Bold)
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

    // Dialogo de confirmacion para eliminar el caso
    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("Eliminar caso") },
            text = { Text("Esta accion eliminara el caso y todas sus entrevistas y evidencias. No se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogoEliminar = false
                    detalleViewModel.eliminarCasoActual(onEliminado = casoEliminado)
                }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialogo para agregar una nueva evidencia
    if (mostrarDialogoEvidencia && caso != null) {
        var nombreArchivo by remember { mutableStateOf("") }
        var tipo by remember { mutableStateOf("") }
        var tamano by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { mostrarDialogoEvidencia = false },
            title = { Text("Nueva evidencia") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nombreArchivo,
                        onValueChange = { nombreArchivo = it },
                        label = { Text("Nombre del archivo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tipo,
                        onValueChange = { tipo = it },
                        label = { Text("Tipo (foto, audio, documento...)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tamano,
                        onValueChange = { tamano = it },
                        label = { Text("Tamano (ej. 2.4 MB)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    detalleViewModel.agregarEvidencia(
                        casoId = caso!!.id,
                        nombreArchivo = nombreArchivo,
                        tipo = tipo,
                        tamano = tamano
                    )
                    mostrarDialogoEvidencia = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEvidencia = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TextoPestana(
    texto: String,
    seleccionada: Boolean,
    accion: () -> Unit
) {
    TextButton(onClick = accion) {
        Text(
            text = texto,
            color = if (seleccionada) Color(0xFF1266D6) else Color.Gray,
            fontSize = 11.sp
        )
    }
}