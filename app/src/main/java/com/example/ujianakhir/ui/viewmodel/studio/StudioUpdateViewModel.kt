package com.example.ujianakhir.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.repository.StudioRepo
import com.example.ujianakhir.ui.viewmodel.studio.InsertStudioUiEvent
import com.example.ujianakhir.ui.viewmodel.studio.InsertStudioUiState
import com.example.ujianakhir.ui.viewmodel.studio.toStudio
import com.example.ujianakhir.ui.viewmodel.studio.toUiStateStudio
import kotlinx.coroutines.launch

class StudioUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val studioRepository: StudioRepo
) : ViewModel() {

    var uiStateUpdate by mutableStateOf(InsertStudioUiState())
        private set

    val idStudioString: String? = savedStateHandle[DestinasiUpdateStudio.idStudio]
    var idStudio: Int = checkNotNull(idStudioString?.toIntOrNull()) {
        "idStudio is missing or not a valid integer"
    }

    init {
        viewModelScope.launch {
            uiStateUpdate = studioRepository.getStudioById(idStudio).data.toUiStateStudio()
        }
    }

    /**
     * Update UI State ketika ada perubahan input dari pengguna.
     */
    fun updateInsertStudioState(insertStudioUiEvent: InsertStudioUiEvent) {
        uiStateUpdate = InsertStudioUiState(insertStudioUiEvent = insertStudioUiEvent)
    }

    /**
     * Fungsi untuk mengedit data studio berdasarkan id.
     */
    fun updateStudio() {
        viewModelScope.launch {
            try {
                studioRepository.updateStudio(idStudio, uiStateUpdate.insertStudioUiEvent.toStudio())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

