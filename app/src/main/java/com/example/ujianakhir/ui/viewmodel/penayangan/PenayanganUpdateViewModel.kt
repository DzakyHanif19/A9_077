package com.example.ujianakhir.ui.viewmodel.penayangan

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.repository.PenayanganRepo
import kotlinx.coroutines.launch

class PenayanganUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val penayanganRepository: PenayanganRepo
) : ViewModel() {

    var uiStateUpdate by mutableStateOf(InsertPenayanganUiState())
        private set

    val idPenayanganString: String? = savedStateHandle["idPenayangan"]
    var idPenayangan: Int = checkNotNull(idPenayanganString?.toIntOrNull()) {
        "idPenayangan is missing or not a valid integer"
    }

    init {
        viewModelScope.launch {
            val response = penayanganRepository.getPenayanganById(idPenayangan)
            if (response.status) {
                uiStateUpdate = InsertPenayanganUiState(insertUiEvent = response.data.toInsertUiEvent())
            }
        }
    }

    /**
     * Update UI State ketika ada perubahan input dari pengguna.
     */
    fun updatePenayanganState(insertPenayanganUiEvent: InsertPenayanganUiEvent) {
        uiStateUpdate = InsertPenayanganUiState(insertUiEvent = insertPenayanganUiEvent)
    }

    /**
     * Fungsi untuk mengedit data penayangan berdasarkan id.
     */
    fun updatePenayangan() {
        viewModelScope.launch {
            try {
                penayanganRepository.updatePenayangan(idPenayangan, uiStateUpdate.insertUiEvent.toPenayangan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}




