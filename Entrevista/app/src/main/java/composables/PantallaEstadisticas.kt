package composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import logic.CasoViewModel

import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PantallaEstadisticas(
    casoViewModel: CasoViewModel,
    volver: () -> Unit
) {

    val casos by casoViewModel.casos.collectAsState()

    val investigacion = casos.count {
        it.estado == "En investigacion"
    }

    val cerrados = casos.count {
        it.estado == "Cerrada"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(20.dp)
    ) {

        Text(
            text = "Estadisticas",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = casos.size.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )

                Text("Casos totales")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = investigacion.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )

                Text("En investigacion")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = cerrados.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )

                Text("Casos cerrados")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Volver")
        }
    }
}