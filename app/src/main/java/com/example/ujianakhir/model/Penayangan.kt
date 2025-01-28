package com.example.ujianakhir.model

import kotlinx.serialization.Serializable

@Serializable
data class PenayanganResponse(
    val status : Boolean,
    val data: List<Penayangan>
)

@Serializable
data class PenayanganDetailResponse(
    val status: Boolean,
    val data: Penayangan
)

@Serializable
data class Penayangan(
    val idPenayangan: Int,
    val idFilm: Int,
    val idStudio: Int,
    val tanggalPenayangan: String,
    val hargaTiket: Double
)