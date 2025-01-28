package com.example.ujianakhir.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.repository.TiketRepo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeTiketUiState {
    data class Success(val tiket: List<Tiket>) : HomeTiketUiState()
    object Error : HomeTiketUiState()
    object Loading : HomeTiketUiState()
}

class HomeTiketViewModel(
    private val tiketRepository: TiketRepo
) : ViewModel() {

    var tiketUiState: HomeTiketUiState by mutableStateOf(HomeTiketUiState.Loading)
        private set

    init {
        getTiket()
    }

    fun getTiket() {
        viewModelScope.launch {
            tiketUiState = HomeTiketUiState.Loading
            try {
                // Mendapatkan data tiket dari repository
                val tiketResponse = tiketRepository.getAllTiket()
                tiketUiState = HomeTiketUiState.Success(tiketResponse.data)
            } catch (e: IOException) {
                tiketUiState = HomeTiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeTiketUiState.Error
            }
        }
    }
}