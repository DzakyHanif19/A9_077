package com.example.ujianakhir.repository

import com.example.ujianakhir.model.Film
import com.example.ujianakhir.model.FilmDetailResponse
import com.example.ujianakhir.model.FilmResponse
import com.example.ujianakhir.service.FilmService


interface FilmRepo {

    suspend fun getAllFilm(): FilmResponse

    suspend fun insertFilm(film: Film)

    suspend fun updateFilm(idFilm: Int, film: Film)

    suspend fun deleteFilm(idFilm: Int)

    suspend fun getFilmById(idFilm: Int): FilmDetailResponse
}
class NetworkFilmRepo(
    private val filmApiService: FilmService
) : FilmRepo {

    override suspend fun insertFilm(film: Film) {
        filmApiService.insertFilm(film)
    }

    override suspend fun updateFilm(idFilm: Int, film: Film) {
        filmApiService.updateFilm(idFilm, film)
    }

    override suspend fun deleteFilm(idFilm: Int) {
        try {
            val response = filmApiService.deleteFilm(idFilm)
            if (!response.isSuccessful) {
                throw Exception("Gagal menghapus film, HTTP Status Code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllFilm(): FilmResponse {
        return filmApiService.getAllFilm()
    }

    override suspend fun getFilmById(idFilm: Int): FilmDetailResponse {
        return filmApiService.getFilmById(idFilm)
    }
}