package com.example.planifyeventos.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.planifyeventos.model.Evento

@Composable
fun EventoCard(evento: Evento, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = evento.imagem,
                contentDescription = evento.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(Modifier.padding(12.dp)) {
                Text(evento.titulo, style = MaterialTheme.typography.titleMedium)
                Text(evento.descricao, fontSize = 14.sp, maxLines = 2)
                Spacer(Modifier.height(8.dp))
                Text("📍 ${evento.local} | ${evento.data_evento} às ${evento.horario}", fontSize = 12.sp)

                // 🔁 Corrigido aqui:
                Text("🎟 R$ %.2f".format(evento.valor_ingresso), fontSize = 12.sp)
            }
        }
    }
}

