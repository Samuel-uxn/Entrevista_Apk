package composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import logic.DetalleCasoViewModel

import androidx.compose.ui.tooling.preview.Preview
@Composable
fun PantallaNuevaEntrevista(
    detalleViewModel: DetalleCasoViewModel,
    casoId: Int,
    volver: () -> Unit
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var rol by remember {
        mutableStateOf("")
    }

    var fecha by remember {
        mutableStateOf("")
    }

    var hallazgos by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(20.dp)
    ) {

        Text(
            text = "Nueva entrevista",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = rol,
            onValueChange = {
                rol = it
            },
            label = {
                Text("Rol")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = {
                fecha = it
            },
            label = {
                Text("Fecha")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = hallazgos,
            onValueChange = {
                hallazgos = it
            },
            label = {
                Text("Hallazgos")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                detalleViewModel.agregarEntrevista(
                    casoId = casoId,
                    nombre = nombre,
                    rol = rol,
                    fecha = fecha,
                    hallazgos = hallazgos
                )

                volver()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Guardar entrevista")
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
