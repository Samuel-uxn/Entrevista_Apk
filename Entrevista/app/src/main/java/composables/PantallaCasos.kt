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
import logic.CasoViewModel

import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PantallaCasos(
    casoViewModel: CasoViewModel,
    volverInicio: () -> Unit,
    nuevoCaso: () -> Unit,
    abrirCaso: (Int) -> Unit
) {

    val casos by casoViewModel.casos.collectAsState()

    var busqueda by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
    ) {

        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF182838))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "▤",
                color = Color.White,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Mis casos",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = nuevoCaso,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1266D6)
                )
            ) {

                Text("+ Nuevo caso")
            }
        }

        // Buscador
        OutlinedTextField(
            value = busqueda,
            onValueChange = {

                busqueda = it
                casoViewModel.buscarCaso(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = {
                Text("Buscar por titulo, tema o fuente...")
            },
            singleLine = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            Text(
                text = "Todos (${casos.size})",
                color = Color(0xFF1266D6),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "En investigacion",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Cerrados",
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(casos) { caso ->

                TarjetaCaso(
                    titulo = caso.titulo,
                    fecha = caso.fecha,
                    estado = caso.estado,
                    descripcion = caso.descripcion,
                    abrir = {
                        abrirCaso(caso.id)
                    }
                )
            }
        }

        BarraInferior(
            inicio = false,
            irInicio = volverInicio,
            irCasos = {}
        )
    }
}

@Composable
fun TarjetaCaso(
    titulo: String,
    fecha: String,
    estado: String,
    descripcion: String,
    abrir: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                abrir()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(65.dp)
                    .background(Color(0xFFB8C3CE)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "FOTO",
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = descripcion,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    maxLines = 1
                )

                Text(
                    text = "▣ $fecha",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = estado,
                color = Color(0xFF8A6500),
                fontSize = 10.sp,
                modifier = Modifier
                    .background(
                        Color(0xFFFFEAB0),
                        RoundedCornerShape(6.dp)
                    )
                    .padding(5.dp)
            )
        }
    }
}

