package com.example.planifyeventos.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planifyeventos.R
import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.model.ResultEvento
import com.example.planifyeventos.screens.components.EventoCard
import com.example.planifyeventos.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Home(navegacao: NavHostController?) {
    val pagerState = rememberPagerState { 5 }
    val pagerItems = listOf(
        R.drawable.belo, R.drawable.belo, R.drawable.belo,
        R.drawable.belo, R.drawable.belo
    )

    val eventosState = remember { mutableStateOf<List<Evento>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        RetrofitFactory().getEventoService().listarEventos()
            .enqueue(object : Callback<ResultEvento> {
                override fun onResponse(call: Call<ResultEvento>, response: Response<ResultEvento>) {
                    if (response.isSuccessful) {
                        eventosState.value = response.body()?.eventos ?: emptyList()
                    } else {
                        Log.e("Home", "Erro no código de resposta: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ResultEvento>, t: Throwable) {
                    Log.e("Home", "Falha na requisição: ${t.message}")
                }
            })
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Topo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(90.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Pesquisar shows, eventos, teatros",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Button (
                onClick = {
                    navegacao?.navigate("perfil")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pe),
                    contentDescription = "Botão com imagem"
                )
            }
        }

        // Carrossel de destaque
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill,
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->
            Image(
                painter = painterResource(pagerItems[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .clickable {
                        Toast.makeText(context, "Imagem: $page", Toast.LENGTH_SHORT).show()
                    },
                contentScale = ContentScale.Crop
            )
        }

        // Indicadore
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                val color = if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }

        // Título da seção
        Text(
            text = "Conheça os Eventos:",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )

        // Lista de cards de eventos
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(eventosState.value) { evento ->
                EventoCard(evento)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    Home(null)
}
