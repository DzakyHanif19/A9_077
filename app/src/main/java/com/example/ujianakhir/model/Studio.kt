package com.example.ujianakhir.model

import kotlinx.serialization.Serializable

@Serializable
data class StudioResponse(
    val status : Boolean,
    val data: List<Studio>
)

@Serializable
data class StudioDetailResponse(
    val status: Boolean,
    val data: Studio
)

@Serializable
data class Studio(
    val idStudio: Int,
    val namaStudio: String,
    val kapasitas: Int
)