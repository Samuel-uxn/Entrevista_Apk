package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import logic.Caso
import logic.CasoViewModel
import com.apks.entrevista.ui.theme.*

@Composable
fun PantallaInicio(viewModel: CasoViewModel) {
    // 1. Conectamos la pantalla al "canal en vivo" de tu base de datos
    val listaCasos by viewModel.casos.collectAsState()

    // 2. Fondo general de la aplicación
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Mis Casos",
            color = AzulClaro,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 3. Lista dinámica (El equivalente moderno al RecyclerView)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaCasos) { caso ->
                TarjetaCaso(caso = caso)
            }
        }
    }
}

// 4. El diseño individual de cada "carpeta" o caso
@Composable
fun TarjetaCaso(caso: Caso) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TarjetaGris),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = caso.titulo,
                color = TextoBlanco,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = caso.descripcion,
                color = TextoBlanco.copy(alpha = 0.7f), // Un blanco un poco más transparente
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Estado y Fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = caso.estado,
                    color = AzulClaro,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = caso.fecha,
                    color = TextoBlanco.copy(alpha = 0.5f)
                )
            }
        }
    }
}