package com.example.ujianakhir.service

import com.example.ujianakhir.model.Film
import com.example.ujianakhir.model.FilmDetailResponse
import com.example.ujianakhir.model.FilmResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FilmService {

    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )

    @GET("bioskop/film")
    suspend fun getAllFilm(): FilmResponse

    @GET("bioskop/film/{idFilm}")
    suspend fun getFilmById(@Path("idFilm") idFilm: Int): FilmDetailResponse

    @POST("bioskop/film")
    suspend fun insertFilm(@Body film: Film): Response<Void>

    @PUT("bioskop/film/{idFilm}")
    suspend fun updateFilm(@Path("idFilm") idFilm: Int, @Body film: Film): Response<Void>

    @DELETE("bioskop/film/{idFilm}")
    suspend fun deleteFilm(@Path("idFilm") idFilm: Int): Response<Void>
}