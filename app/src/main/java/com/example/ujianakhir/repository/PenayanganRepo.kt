package com.example.ujianakhir.repository

import com.example.ujianakhir.model.Penayangan
import com.example.ujianakhir.model.PenayanganDetailResponse
import com.example.ujianakhir.model.PenayanganResponse
import com.example.ujianakhir.service.PenayanganService

interface PenayanganRepo {

    suspend fun getAllPenayangan(): PenayanganResponse

    suspend fun insertPenayangan(penayangan: Penayangan)

    suspend fun updatePenayangan(idPenayangan: Int, penayangan: Penayangan)

    suspend fun deletePenayangan(idPenayangan: Int)

    suspend fun getPenayanganById(idPenayangan: Int): PenayanganDetailResponse
}
class NetworkPenayanganRepo(
    private val penayanganApiService: PenayanganService
) : PenayanganRepo {

    override suspend fun insertPenayangan(penayangan: Penayangan) {
        penayanganApiService.insertPenayangan(penayangan)
    }

    override suspend fun updatePenayangan(idPenayangan: Int, penayangan: Penayangan) {
        penayanganApiService.updatePenayangan(idPenayangan, penayangan)
    }

    override suspend fun deletePenayangan(idPenayangan: Int) {
        try {
            val response = penayanganApiService.deletePenayangan(idPenayangan)
            if (!response.isSuccessful) {
                throw Exception("Gagal menghapus penayangan, HTTP Status Code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllPenayangan(): PenayanganResponse {
        return penayanganApiService.getAllPenayangan()
    }

    override suspend fun getPenayanganById(idPenayangan: Int): PenayanganDetailResponse {
        return penayanganApiService.getPenayanganById(idPenayangan)
    }
}