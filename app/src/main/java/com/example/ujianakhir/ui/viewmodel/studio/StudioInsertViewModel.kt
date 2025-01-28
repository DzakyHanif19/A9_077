    package com.example.ujianakhir.ui.viewmodel.studio

    import androidx.compose.runtime.*
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.ujianakhir.model.Studio
    import com.example.ujianakhir.repository.StudioRepo
    import kotlinx.coroutines.launch

    class StudioInsertViewModel(private val studioRepo: StudioRepo) : ViewModel() {

        var uiStateStudio by mutableStateOf(InsertStudioUiState())
            private set

        /**
         * Memperbarui state UI berdasarkan event dari pengguna.
         */
        fun updateInsertStudioState(insertStudioUiEvent: InsertStudioUiEvent) {
            uiStateStudio = InsertStudioUiState(insertStudioUiEvent = insertStudioUiEvent)
        }

        /**
         * Menambahkan studio baru ke repository.
         */
        fun insertStudio() {
            viewModelScope.launch {
                try {
                    studioRepo.insertStudio(uiStateStudio.insertStudioUiEvent.toStudio())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    data class InsertStudioUiState(
        val insertStudioUiEvent: InsertStudioUiEvent = InsertStudioUiEvent()
    )

    data class InsertStudioUiEvent(
        val idStudio: Int? = null,
        val namaStudio: String = "",
        val kapasitas: Int = 0
    )

    fun InsertStudioUiEvent.toStudio(): Studio = Studio(
        idStudio = idStudio ?: 0, // ID akan diatur oleh backend jika null
        namaStudio = namaStudio,
        kapasitas = kapasitas
    )

    fun Studio.toUiStateStudio(): InsertStudioUiState = InsertStudioUiState(
        insertStudioUiEvent = toInsertStudioUiEvent()
    )

    fun Studio.toInsertStudioUiEvent(): InsertStudioUiEvent = InsertStudioUiEvent(
        idStudio = idStudio,
        namaStudio = namaStudio,
        kapasitas = kapasitas
    )
