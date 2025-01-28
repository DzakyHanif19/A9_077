package com.example.ujianakhir.ui.viewmodel.film

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Film
import com.example.ujianakhir.repository.FilmRepo
import kotlinx.coroutines.launch

class FilmInsertViewModel(private val filmRepo: FilmRepo) : ViewModel() {

    var uiStateFilm by mutableStateOf(InsertFilmUiState())
        private set

    /**
     * Memperbarui state UI berdasarkan event dari pengguna.
     */
    fun updateInsertFilmState(insertFilmUiEvent: InsertFilmUiEvent) {
        uiStateFilm = InsertFilmUiState(insertFilmUiEvent = insertFilmUiEvent)
    }

    /**
     * Menambahkan film baru ke repository.
     */
    fun insertFilm() {
        viewModelScope.launch {
            try {
                filmRepo.insertFilm(uiStateFilm.insertFilmUiEvent.toFilm())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
data class InsertFilmUiState(
    val insertFilmUiEvent: InsertFilmUiEvent = InsertFilmUiEvent()
)

data class InsertFilmUiEvent(
    val idFilm: Int? = null,
    val judulFilm: String = "",
    val durasi: Int = 0,
    val deskripsi: String = "",
    val genre: String = "",
    val ratingUsia: String = ""
)

fun InsertFilmUiEvent.toFilm(): Film = Film(
    idFilm = idFilm ?: 0, // ID akan diatur oleh backend jika null
    judulFilm = judulFilm,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    ratingUsia = ratingUsia
)
fun Film.toUiStateFilm(): InsertFilmUiState = InsertFilmUiState(
    insertFilmUiEvent = toInsertFilmUiEvent()
)
fun Film.toInsertFilmUiEvent(): InsertFilmUiEvent = InsertFilmUiEvent(
    idFilm = idFilm,
    judulFilm = judulFilm,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    ratingUsia = ratingUsia
)
