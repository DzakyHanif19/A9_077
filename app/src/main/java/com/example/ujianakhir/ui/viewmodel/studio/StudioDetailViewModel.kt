package com.example.ujianakhir.ui.viewmodel.studio

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Studio
import com.example.ujianakhir.repository.StudioRepo
import kotlinx.coroutines.launch
import java.io.IOException

sealed class StudioDetailUiState {
    data class Success(val studio: Studio) : StudioDetailUiState()
    object Error : StudioDetailUiState()
    object Loading : StudioDetailUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class StudioDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val studioRepo: StudioRepo
) : ViewModel() {

    // Mendapatkan `idStudio` dari `SavedStateHandle`
    private val idStudioString: String? = savedStateHandle["idStudio"]
    private val idStudio: Int = checkNotNull(idStudioString?.toIntOrNull()) {
        "idStudio is missing or not a valid integer"
    }

    var studioDetailUiState: StudioDetailUiState by mutableStateOf(StudioDetailUiState.Loading)
        private set

    init {
        getStudioById()
    }

    /**
     * Mengambil detail studio berdasarkan `idStudio`.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getStudioById() {
        viewModelScope.launch {
            studioDetailUiState = StudioDetailUiState.Loading
            studioDetailUiState = try {
                val response = studioRepo.getStudioById(idStudio)
                StudioDetailUiState.Success(response.data)
            } catch (e: IOException) {
                StudioDetailUiState.Error
            } catch (e: HttpException) {
                StudioDetailUiState.Error
            }
        }
    }
}
