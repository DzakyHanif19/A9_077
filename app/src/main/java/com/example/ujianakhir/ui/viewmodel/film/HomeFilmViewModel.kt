package com.example.ujianakhir.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Film
import com.example.ujianakhir.repository.FilmRepo
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeFilmUiState {
    data class Success(val films: List<Film>) : HomeFilmUiState()
    object Error : HomeFilmUiState()
    object Loading : HomeFilmUiState()
}

class HomeFilmViewModel(private val filmRepo: FilmRepo) : ViewModel() {
    var filmUiState: HomeFilmUiState by mutableStateOf(HomeFilmUiState.Loading)
        private set

    init {
        getAllFilms()
    }

    fun getAllFilms() {
        viewModelScope.launch {
            filmUiState = HomeFilmUiState.Loading
            filmUiState = try {
                val response = filmRepo.getAllFilm()
                if (response.status) {
                    HomeFilmUiState.Success(response.data)
                } else {
                    HomeFilmUiState.Error
                }
            } catch (e: IOException) {
                HomeFilmUiState.Error
            } catch (e: Exception) {
                HomeFilmUiState.Error
            }
        }
    }

    fun deleteFilm(idFilm: Int) {
        viewModelScope.launch {
            try {
                filmRepo.deleteFilm(idFilm)
                getAllFilms() // Refresh list after deletion
            } catch (e: Exception) {
                filmUiState = HomeFilmUiState.Error
            }
        }
    }
}
