package com.example.ujianakhir.di

import com.example.ujianakhir.repository.FilmRepo
import com.example.ujianakhir.repository.NetworkFilmRepo
import com.example.ujianakhir.repository.NetworkPenayanganRepo
import com.example.ujianakhir.repository.NetworkStudioRepo
import com.example.ujianakhir.repository.NetworkTiketRepo
import com.example.ujianakhir.repository.PenayanganRepo
import com.example.ujianakhir.repository.StudioRepo
import com.example.ujianakhir.repository.TiketRepo
import com.example.ujianakhir.service.FilmService
import com.example.ujianakhir.service.PenayanganService
import com.example.ujianakhir.service.StudioService
import com.example.ujianakhir.service.TiketService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val filmRepo: FilmRepo
    val penayanganRepo: PenayanganRepo
    val studioRepo: StudioRepo
    val tiketRepo: TiketRepo
}

class BioskopContainerApp : AppContainer {

    private val baseUrl = "http://localhost:8080/api" // Sesuaikan dengan endpoint API Anda

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Services
    private val filmService: FilmService by lazy { retrofit.create(FilmService::class.java) }
    private val penayanganService: PenayanganService by lazy { retrofit.create(PenayanganService::class.java) }
    private val studioService: StudioService by lazy { retrofit.create(StudioService::class.java) }
    private val tiketService: TiketService by lazy { retrofit.create(TiketService::class.java) }

    // Repositories
    override val filmRepo: FilmRepo by lazy { NetworkFilmRepo(filmService) }
    override val penayanganRepo: PenayanganRepo by lazy { NetworkPenayanganRepo(penayanganService) }
    override val studioRepo: StudioRepo by lazy { NetworkStudioRepo(studioService) }
    override val tiketRepo: TiketRepo by lazy { NetworkTiketRepo(tiketService) }
}