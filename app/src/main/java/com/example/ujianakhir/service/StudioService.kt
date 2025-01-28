package com.example.ujianakhir.service

import com.example.ujianakhir.model.Studio
import com.example.ujianakhir.model.StudioDetailResponse
import com.example.ujianakhir.model.StudioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudioService {

    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )

    @GET("bioskop/studio")
    suspend fun getAllStudio(): StudioResponse

    @GET("bioskop/studio/{idStudio}")
    suspend fun getStudioById(@Path("idStudio") idStudio: Int): StudioDetailResponse

    @POST("bioskop/studio")
    suspend fun insertStudio(@Body studio: Studio): Response<Void>

    @PUT("bioskop/studio/{idStudio}")
    suspend fun updateStudio(@Path("idStudio") idStudio: Int, @Body studio: Studio): Response<Void>

    @DELETE("bioskop/studio/{idStudio}")
    suspend fun deleteStudio(@Path("idStudio") idStudio: Int): Response<Void>
}