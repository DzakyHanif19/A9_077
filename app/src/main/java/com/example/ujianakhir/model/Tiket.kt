package com.example.ujianakhir.model

import kotlinx.serialization.Serializable

@Serializable
data class TiketResponse(
    val status : Boolean,
    val data: List<Tiket>
)

@Serializable
data class TiketDetailResponse(
    val status: Boolean,
    val data: Tiket
)

@Serializable
data class Tiket(
    val idTiket: Int,
    val idPenayangan: Int,
    val jumlahTiket: Int,
    val totalHarga: Double,
    val statusPembayaran: String
)
