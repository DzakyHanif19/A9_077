package com.example.ujianakhir.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.repository.TiketRepo
import kotlinx.coroutines.launch

class InsertTiketViewModel(
    private val tiketRepo: TiketRepo
) : ViewModel() {
    var uiTiketState by mutableStateOf(InsertTiketUiState())
        private set
    var tiketList by mutableStateOf<List<Tiket>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadTiketList()
    }

    private fun loadTiketList() {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val tiketResponse = tiketRepo.getAllTiket()
                tiketList = tiketResponse.data
            } catch (e: Exception) {
                errorMessage = "Gagal memuat data tiket: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        uiTiketState = uiTiketState.copy(insertTiketUiEvent = insertTiketUiEvent)
    }

    fun insertTiket() {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                tiketRepo.insertTiket(uiTiketState.insertTiketUiEvent.toTiket())
            } catch (e: Exception) {
                errorMessage = "Gagal memasukkan tiket: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}

data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent()
)

data class InsertTiketUiEvent(
    val idPenayangan: Int = 0,
    val jumlahTiket: Int = 0,
    val totalHarga: Double = 0.0,
    val statusPembayaran: String = ""
)

fun InsertTiketUiEvent.toTiket(): Tiket = Tiket(
    idPenayangan = idPenayangan,
    jumlahTiket = jumlahTiket,
    totalHarga = totalHarga,
    statusPembayaran = statusPembayaran
)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = toInsertTiketUiEvent()
)

fun Tiket.toInsertTiketUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent(
    idPenayangan = idPenayangan,
    jumlahTiket = jumlahTiket,
    totalHarga = totalHarga,
    statusPembayaran = statusPembayaran
)
