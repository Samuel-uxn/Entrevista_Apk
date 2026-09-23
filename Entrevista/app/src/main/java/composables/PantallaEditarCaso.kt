package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import logic.DetalleCasoViewModel

@Composable
fun PantallaEditarCaso(
    detalleViewModel: DetalleCasoViewModel,
    volver: () -> Unit,
    guardado: () -> Unit
) {

    val caso by detalleViewModel.casoActual.collectAsState()

    var titulo by remember { mutableStateOf(caso?.titulo ?: "") }
    var descripcion by remember { mutableStateOf(caso?.descripcion ?: "") }
    var fecha by remember { mutableStateOf(caso?.fecha ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(20.dp)
    ) {

        Text(
            text = "Editar caso",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Titulo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripcion") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val casoActual = caso
                if (casoActual != null) {
                    detalleViewModel.editarCaso(
                        casoId = casoActual.id,
                        titulo = titulo,
                        descripcion = descripcion,
                        fecha = fecha
                    )
                }
                guardado()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}