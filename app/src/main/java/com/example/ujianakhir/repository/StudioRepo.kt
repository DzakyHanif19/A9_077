package com.example.ujianakhir.repository

import com.example.ujianakhir.model.Studio
import com.example.ujianakhir.model.StudioDetailResponse
import com.example.ujianakhir.model.StudioResponse
import com.example.ujianakhir.service.StudioService

interface StudioRepo {

    suspend fun getAllStudio(): StudioResponse

    suspend fun insertStudio(studio: Studio)

    suspend fun updateStudio(idStudio: Int, studio: Studio)

    suspend fun deleteStudio(idStudio: Int)

    suspend fun getStudioById(idStudio: Int): StudioDetailResponse
}
class NetworkStudioRepo(
    private val studioApiService: StudioService
) : StudioRepo {

    override suspend fun insertStudio(studio: Studio) {
        studioApiService.insertStudio(studio)
    }

    override suspend fun updateStudio(idStudio: Int, studio: Studio) {
        studioApiService.updateStudio(idStudio, studio)
    }

    override suspend fun deleteStudio(idStudio: Int) {
        try {
            val response = studioApiService.deleteStudio(idStudio)
            if (!response.isSuccessful) {
                throw Exception("Gagal menghapus studio, HTTP Status Code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllStudio(): StudioResponse {
        return studioApiService.getAllStudio()
    }

    override suspend fun getStudioById(idStudio: Int): StudioDetailResponse {
        return studioApiService.getStudioById(idStudio)
    }
}