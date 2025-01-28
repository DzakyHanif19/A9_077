package com.example.ujianakhir.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Studio
import com.example.ujianakhir.repository.StudioRepo
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeStudioUiState {
    data class Success(val studios: List<Studio>) : HomeStudioUiState()
    object Error : HomeStudioUiState()
    object Loading : HomeStudioUiState()
}

class HomeStudioViewModel(private val studioRepo: StudioRepo) : ViewModel() {
    var studioUiState: HomeStudioUiState by mutableStateOf(HomeStudioUiState.Loading)
        private set

    init {
        getAllStudios()
    }

    /**
     * Get all studios from repository.
     */
    fun getAllStudios() {
        viewModelScope.launch {
            studioUiState = HomeStudioUiState.Loading
            studioUiState = try {
                val response = studioRepo.getAllStudio()
                if (response.status) {
                    HomeStudioUiState.Success(response.data)
                } else {
                    HomeStudioUiState.Error
                }
            } catch (e: IOException) {
                HomeStudioUiState.Error
            } catch (e: Exception) {
                HomeStudioUiState.Error
            }
        }
    }

    /**
     * Delete studio by ID.
     */
    fun deleteStudio(idStudio: Int) {
        viewModelScope.launch {
            try {
                studioRepo.deleteStudio(idStudio)
                getAllStudios() // Refresh list after deletion
            } catch (e: Exception) {
                studioUiState = HomeStudioUiState.Error
            }
        }
    }
}
