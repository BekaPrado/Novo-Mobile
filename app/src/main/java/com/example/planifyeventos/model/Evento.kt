package com.example.planifyeventos.model

data class Evento(
    val id_evento: Int,
    val titulo: String,
    val descricao: String,
    val data_evento: String,
    val horario: String,
    val local: String,
    val imagem: String,
    val limite_participante: Int,
    val valor_ingresso: Double,
    val id_usuario: Int,
    val id_estado: Int
)
