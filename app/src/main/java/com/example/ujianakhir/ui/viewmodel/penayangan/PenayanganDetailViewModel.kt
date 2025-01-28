package com.example.ujianakhir.ui.viewmodel.penayangan

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Penayangan
import com.example.ujianakhir.repository.PenayanganRepo
import kotlinx.coroutines.launch
import java.io.IOException

sealed class PenayanganDetailUiState {
    data class Success(val penayangan: Penayangan) : PenayanganDetailUiState()
    object Error : PenayanganDetailUiState()
    object Loading : PenayanganDetailUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PenayanganDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val penayanganRepo: PenayanganRepo
) : ViewModel() {

    // Mendapatkan `idPenayangan` dari `SavedStateHandle`
    private val idPenayanganString: String? = savedStateHandle[DestinasiDetail.idPenayangan]
    private val idPenayangan: Int = checkNotNull(idPenayanganString?.toIntOrNull()) {
        "idPenayangan is missing or not a valid integer"
    }

    var penayanganDetailUiState: PenayanganDetailUiState by mutableStateOf(PenayanganDetailUiState.Loading)
        private set

    init {
        getPenayanganById()
    }

    /**
     * Mengambil detail penayangan berdasarkan `idPenayangan`.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPenayanganById() {
        viewModelScope.launch {
            penayanganDetailUiState = PenayanganDetailUiState.Loading
            penayanganDetailUiState = try {
                val response = penayanganRepo.getPenayanganById(idPenayangan)
                PenayanganDetailUiState.Success(response.data)
            } catch (e: IOException) {
                PenayanganDetailUiState.Error
            } catch (e: HttpException) {
                PenayanganDetailUiState.Error
            }
        }
    }
}
