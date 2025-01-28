package com.example.ujianakhir.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Penayangan
import com.example.ujianakhir.repository.PenayanganRepo
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenayanganUiState {
    data class Success(val penayangans: List<Penayangan>) : HomePenayanganUiState()
    object Error : HomePenayanganUiState()
    object Loading : HomePenayanganUiState()
}

class HomePenayanganViewModel(private val penayanganRepo: PenayanganRepo) : ViewModel() {
    var penayanganUiState: HomePenayanganUiState by mutableStateOf(HomePenayanganUiState.Loading)
        private set

    init {
        getAllPenayangan()
    }

    /**
     * Mengambil semua data penayangan dari repository
     */
    fun getAllPenayangan() {
        viewModelScope.launch {
            penayanganUiState = HomePenayanganUiState.Loading
            penayanganUiState = try {
                val response = penayanganRepo.getAllPenayangan()
                if (response.status) {
                    HomePenayanganUiState.Success(response.data)
                } else {
                    HomePenayanganUiState.Error
                }
            } catch (e: IOException) {
                HomePenayanganUiState.Error
            } catch (e: Exception) {
                HomePenayanganUiState.Error
            }
        }
    }

    /**
     * Menghapus data penayangan berdasarkan ID dan memperbarui daftar
     */
    fun deletePenayangan(idPenayangan: Int) {
        viewModelScope.launch {
            try {
                penayanganRepo.deletePenayangan(idPenayangan)
                getAllPenayangan() // Refresh daftar setelah penghapusan
            } catch (e: Exception) {
                penayanganUiState = HomePenayanganUiState.Error
            }
        }
    }
}
