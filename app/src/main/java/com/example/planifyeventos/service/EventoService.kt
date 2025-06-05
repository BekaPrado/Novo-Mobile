package com.example.planifyeventos.service

import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.model.ResultEvento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface EventoService {
    @GET("eventos")
    fun listarEventos(): Call<ResultEvento>

    @POST("evento")
    @Headers("Content-Type: application/json")
    fun inserirEvento(@Body evento: Evento): Call<Evento>
}
