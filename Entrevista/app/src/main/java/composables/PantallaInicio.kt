package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PantallaInicio(
    irACasos: () -> Unit,
    nuevoCaso: () -> Unit,
    nuevaEntrevista: () -> Unit,
    estadisticas: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
    ) {

        // ENCABEZADO

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Spacer(modifier = Modifier.width(10.dp))

            Column {

                Text(
                    text = "NotaViva",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF26374A)
                )

                Text(
                    text = "Historias que importan",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "●",
                fontSize = 30.sp,
                color = Color.Gray
            )
        }

        // PORTADA

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(Color(0xFF45515C))
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "Hola, periodista",
                fontSize = 27.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Organiza tus entrevistas y\nconstruye tus historias.",
                fontSize = 15.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // PRIMERA FILA

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            TarjetaInicio(
                icono = "▣",
                titulo = "Nuevo caso",
                descripcion = "Crea un nuevo tema\nde investigacion",
                modifier = Modifier.weight(1f),
                accion = nuevoCaso
            )

            TarjetaInicio(
                icono = "♩",
                titulo = "Nueva entrevista",
                descripcion = "Registra una conversacion",
                modifier = Modifier.weight(1f),
                accion = nuevaEntrevista
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SEGUNDA FILA

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            TarjetaInicio(
                icono = "■",
                titulo = "Mis casos",
                descripcion = "Revisa y gestiona\ntus investigaciones",
                modifier = Modifier.weight(1f),
                accion = irACasos
            )

            TarjetaInicio(
                icono = "▥",
                titulo = "Estadisticas",
                descripcion = "Entrevistas, casos\ny avances",
                modifier = Modifier.weight(1f),
                accion = estadisticas
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "\"El periodismo es el primer\nborrador de la historia.\"",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = "Philip Graham",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        BarraInferior(
            inicio = true,
            irInicio = {},
            irCasos = irACasos
        )
    }
}


@Composable
fun TarjetaInicio(
    icono: String,
    titulo: String,
    descripcion: String,
    modifier: Modifier,
    accion: () -> Unit
) {

    Card(
        modifier = modifier
            .height(105.dp)
            .clickable {
                accion()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = icono,
                fontSize = 25.sp
            )

            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )

            Text(
                text = descripcion,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BarraInferior(
    inicio: Boolean,
    irInicio: () -> Unit,
    irCasos: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        // 1. Botón de Inicio
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { irInicio() }
        ) {
            Text(
                text = "?", // (Aquí va tu ícono)
                fontSize = 24.sp,
                color = if (inicio) Color(0xFF1266D6) else Color.Gray
            )
            Text(
                text = "Inicio",
                fontSize = 11.sp
            )
        }

        // 2. Botón de Casos
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { irCasos() }
        ) {
            Text(
                text = "?", // (Aquí va tu ícono)
                fontSize = 24.sp,
                color = if (!inicio) Color(0xFF1266D6) else Color.Gray
            )
            Text(
                text = "Casos",
                fontSize = 11.sp
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun VistaPreviaInicio() {

    PantallaInicio(
        irACasos = {},
        nuevoCaso = {},
        nuevaEntrevista = {},
        estadisticas = {}
    )
}

