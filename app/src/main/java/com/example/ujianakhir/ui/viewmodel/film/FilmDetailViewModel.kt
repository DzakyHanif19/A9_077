package com.example.ujianakhir.ui.viewmodel.film

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Film
import com.example.ujianakhir.repository.FilmRepo
import com.example.ujianakhir.ui.view.film.DestinasiDetailFilm
import kotlinx.coroutines.launch
import java.io.IOException

sealed class FilmDetailUiState {
    data class Success(val film: Film) : FilmDetailUiState()
    object Error : FilmDetailUiState()
    object Loading : FilmDetailUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class FilmDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepo: FilmRepo
) : ViewModel() {

    // Retrieve `idFilm` from the `SavedStateHandle`
    private val idFilmString: String? = savedStateHandle[DestinasiDetailFilm.IDFILM]
    private val idFilm: Int = checkNotNull(idFilmString?.toIntOrNull()) {
        "idFilm is missing or not a valid integer"
    }

    var filmDetailUiState: FilmDetailUiState by mutableStateOf(FilmDetailUiState.Loading)
        private set

    init {
        getFilmById()
    }

    /**
     * Fetch the film detail based on the `idFilm`.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getFilmById() {
        viewModelScope.launch {
            filmDetailUiState = FilmDetailUiState.Loading
            filmDetailUiState = try {
                val response = filmRepo.getFilmById(idFilm)
                FilmDetailUiState.Success(response.data)
            } catch (e: IOException) {
                FilmDetailUiState.Error
            } catch (e: HttpException) {
                FilmDetailUiState.Error
            }
        }
    }
}
