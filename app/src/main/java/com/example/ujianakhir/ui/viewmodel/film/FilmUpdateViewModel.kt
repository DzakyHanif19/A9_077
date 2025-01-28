package com.example.ujianakhir.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Film
import com.example.ujianakhir.repository.FilmRepo
import com.example.ujianakhir.ui.viewmodel.film.InsertFilmUiEvent
import com.example.ujianakhir.ui.viewmodel.film.InsertFilmUiState
import com.example.ujianakhir.ui.viewmodel.film.toFilm
import com.example.ujianakhir.ui.viewmodel.film.toUiStateFilm
import kotlinx.coroutines.launch

class FilmUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepo
) : ViewModel() {

    var uiStateUpdate by mutableStateOf(InsertFilmUiState())
        private set

    val idFilmString: String? = savedStateHandle[DestinasiUpdateFilm.idFilm]
    var idFilm: Int = checkNotNull(idFilmString?.toIntOrNull()) {
        "idFilm is missing or not a valid integer"
    }

    init {
        viewModelScope.launch {
            uiStateUpdate = filmRepository.getFilmById(idFilm).data.toUiStateFilm()
        }
    }

    /**
     * Update UI State ketika ada perubahan input dari pengguna.
     */
    fun updateInsertFilmState(insertFilmUiEvent: InsertFilmUiEvent) {
        uiStateUpdate = InsertFilmUiState(insertFilmUiEvent = insertFilmUiEvent)
    }

    /**
     * Fungsi untuk mengedit data film berdasarkan id.
     */
    fun updateFilm() {
        viewModelScope.launch {
            try {
                filmRepository.updateFilm(idFilm, uiStateUpdate.insertFilmUiEvent.toFilm())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

