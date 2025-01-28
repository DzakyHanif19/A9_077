package com.example.ujianakhir.model

import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    val status : Boolean,
    val data: List<Film>
)

@Serializable
data class FilmDetailResponse(
    val status: Boolean,
    val data: Film
)
@Serializable
data class Film(
    val idFilm: Int,
    val judulFilm: String,
    val durasi: Int,
    val deskripsi: String,
    val genre: String,
    val ratingUsia: String
)