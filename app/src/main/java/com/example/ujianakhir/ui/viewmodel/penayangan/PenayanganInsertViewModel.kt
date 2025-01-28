package com.example.ujianakhir.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Penayangan
import com.example.ujianakhir.repository.PenayanganRepo
import kotlinx.coroutines.launch

class PenayanganInsertViewModel(private val penayanganRepo: PenayanganRepo) : ViewModel() {
    var uiState by mutableStateOf(InsertPenayanganUiState())
        private set

    fun updateInsertPenayanganState(insertUiEvent: InsertPenayanganUiEvent) {
        uiState = InsertPenayanganUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPenayangan() {
        viewModelScope.launch {
            try {
                penayanganRepo.insertPenayangan(uiState.insertUiEvent.toPenayangan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenayanganUiState(
    val insertUiEvent: InsertPenayanganUiEvent = InsertPenayanganUiEvent()
)

data class InsertPenayanganUiEvent(
    val idFilm: Int = 0,
    val idStudio: Int = 0,
    val tanggalPenayangan: String = "",
    val hargaTiket: Double = 0.0
)

fun InsertPenayanganUiEvent.toPenayangan(): Penayangan = Penayangan(
    idPenayangan = 0, // Assuming ID is auto-generated
    idFilm = idFilm,
    idStudio = idStudio,
    tanggalPenayangan = tanggalPenayangan,
    hargaTiket = hargaTiket
)

fun Penayangan.toUiState(): InsertPenayanganUiState = InsertPenayanganUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Penayangan.toInsertUiEvent(): InsertPenayanganUiEvent = InsertPenayanganUiEvent(
    idFilm = idFilm,
    idStudio = idStudio,
    tanggalPenayangan = tanggalPenayangan,
    hargaTiket = hargaTiket
)
