package com.example.ujianakhir.repository

import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.model.TiketDetailResponse
import com.example.ujianakhir.model.TiketResponse
import com.example.ujianakhir.service.TiketService

interface TiketRepo {

    suspend fun getAllTiket(): TiketResponse

    suspend fun insertTiket(tiket: Tiket)

    suspend fun updateTiket(idTiket: Int, tiket: Tiket)

    suspend fun deleteTiket(idTiket: Int)

    suspend fun getTiketById(idTiket: Int): TiketDetailResponse
}
class NetworkTiketRepo(
    private val tiketApiService: TiketService
) : TiketRepo {

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(idTiket: Int, tiket: Tiket) {
        tiketApiService.updateTiket(idTiket, tiket)
    }

    override suspend fun deleteTiket(idTiket: Int) {
        try {
            val response = tiketApiService.deleteTiket(idTiket)
            if (!response.isSuccessful) {
                throw Exception("Gagal menghapus tiket, HTTP Status Code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllTiket(): TiketResponse {
        return tiketApiService.getAllTiket()
    }

    override suspend fun getTiketById(idTiket: Int): TiketDetailResponse {
        return tiketApiService.getTiketById(idTiket)
    }
}