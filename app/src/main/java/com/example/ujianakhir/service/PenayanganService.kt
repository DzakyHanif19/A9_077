package com.example.ujianakhir.service

import com.example.ujianakhir.model.Penayangan
import com.example.ujianakhir.model.PenayanganDetailResponse
import com.example.ujianakhir.model.PenayanganResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenayanganService {

    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )

    @GET("bioskop/penayangan")
    suspend fun getAllPenayangan(): PenayanganResponse

    @GET("bioskop/penayangan/{idPenayangan}")
    suspend fun getPenayanganById(@Path("idPenayangan") idPenayangan: Int): PenayanganDetailResponse

    @POST("bioskop/penayangan")
    suspend fun insertPenayangan(@Body penayangan: Penayangan): Response<Void>

    @PUT("bioskop/penayangan/{idPenayangan}")
    suspend fun updatePenayangan(@Path("idPenayangan") idPenayangan: Int, @Body penayangan: Penayangan): Response<Void>

    @DELETE("bioskop/penayangan/{idPenayangan}")
    suspend fun deletePenayangan(@Path("idPenayangan") idPenayangan: Int): Response<Void>
}