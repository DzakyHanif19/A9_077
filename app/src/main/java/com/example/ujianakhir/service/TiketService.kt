package com.example.ujianakhir.service

import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.model.TiketDetailResponse
import com.example.ujianakhir.model.TiketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketService {

    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )

    @GET("bioskop/tiket")
    suspend fun getAllTiket(): TiketResponse

    @GET("bioskop/tiket/{idTiket}")
    suspend fun getTiketById(@Path("idTiket") idTiket: Int): TiketDetailResponse

    @POST("bioskop/tiket")
    suspend fun insertTiket(@Body tiket: Tiket): Response<Void>

    @PUT("bioskop/tiket/{idTiket}")
    suspend fun updateTiket(@Path("idTiket") idTiket: Int, @Body tiket: Tiket): Response<Void>

    @DELETE("bioskop/tiket/{idTiket}")
    suspend fun deleteTiket(@Path("idTiket") idTiket: Int): Response<Void>
}