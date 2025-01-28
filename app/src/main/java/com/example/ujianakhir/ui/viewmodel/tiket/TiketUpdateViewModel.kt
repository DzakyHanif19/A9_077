package com.example.ujianakhir.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.repository.TiketRepo
import com.example.ujianakhir.model.TiketResponse
import com.example.ujianakhir.model.TiketDetailResponse
import kotlinx.coroutines.launch

sealed class TiketUiState {
    data class Success(val tiket: Tiket) : TiketUiState()
    object Error : TiketUiState()
    object Loading : TiketUiState()
}

class TiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepo
) : ViewModel() {

    var tiketState by mutableStateOf<TiketUiState>(TiketUiState.Loading)
        private set
    var tiketList by mutableStateOf<List<Tiket>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _idTiket: Int = checkNotNull(savedStateHandle["IDTIKET"])

    init {
        loadTiketById()
        loadAllTikets()
    }

    // Load Tiket by ID
    fun loadTiketById() {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val tiketResponse: TiketDetailResponse = tiketRepository.getTiketById(_idTiket)
                tiketState = TiketUiState.Success(tiketResponse.data)
            } catch (e: Exception) {
                errorMessage = "Gagal memuat tiket: ${e.message}"
                tiketState = TiketUiState.Error
            } finally {
                isLoading = false
            }
        }
    }

    // Load all Tikets
    private fun loadAllTikets() {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val tiketResponse: TiketResponse = tiketRepository.getAllTiket()
                tiketList = tiketResponse.data
            } catch (e: Exception) {
                errorMessage = "Gagal memuat data tiket: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Insert new Tiket
    fun insertTiket(tiket: Tiket) {
        viewModelScope.launch {
            try {
                tiketRepository.insertTiket(tiket)
                loadAllTikets() // reload Tiket list after insert
            } catch (e: Exception) {
                errorMessage = "Gagal menambahkan tiket: ${e.message}"
            }
        }
    }

    // Update existing Tiket
    fun updateTiket(tiket: Tiket) {
        viewModelScope.launch {
            try {
                tiketRepository.updateTiket(_idTiket, tiket)
                loadTiketById() // reload specific Tiket after update
            } catch (e: Exception) {
                errorMessage = "Gagal mengupdate tiket: ${e.message}"
            }
        }
    }

    // Delete Tiket
    fun deleteTiket() {
        viewModelScope.launch {
            try {
                tiketRepository.deleteTiket(_idTiket)
                loadAllTikets() // reload Tiket list after delete
            } catch (e: Exception) {
                errorMessage = "Gagal menghapus tiket: ${e.message}"
            }
        }
    }
}
fun updateInsertTiketState(tiketUiState: TiketUiState) {
    var tiketState = tiketUiState
}
