package com.example.ujianakhir.ui.view.penayangan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganUpdateViewModel
import com.example.ujianakhir.ui.viewmodel.penayangan.InsertPenayanganUiState
import com.example.ujianakhir.ui.customwidget.DatePickerField
import com.example.ujianakhir.ui.customwidget.DropdownSelector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.ujianakhir.ui.viewmodel.penayangan.InsertPenayanganUiEvent
import com.example.ujianakhir.ui.customwidget.TopAppBar

object DestinasiUpdatePenayangan : DestinasiNavigasi {
    override val route = "update_penayangan"
    override val titleRes = "Update Penayangan"
    val descriptionRes = "Perbarui data penayangan"
    const val ID_PENAYANGAN = "idPenayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePenayanganScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PenayanganUpdateViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdatePenayangan.titleRes,
                description = DestinasiUpdatePenayangan.descriptionRes,
                canNavigateBack = true,
                navigateUp = onBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        PenayanganForm(
            modifier = Modifier.padding(padding),
            uiState = viewModel.uiStateUpdate,
            onValueChange = viewModel::updatePenayanganState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePenayangan()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}

@Composable
fun PenayanganForm(
    uiState: InsertPenayanganUiState,
    onValueChange: (InsertPenayanganUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var tanggalPenayangan by remember { mutableStateOf(uiState.insertUiEvent.tanggalPenayangan) }

        DatePickerField(
            label = "Tanggal Penayangan",
            selectedDate = tanggalPenayangan,
            onDateSelected = {
                tanggalPenayangan = it
                onValueChange(uiState.insertUiEvent.copy(tanggalPenayangan = it))
            }
        )

        OutlinedTextField(
            value = uiState.insertUiEvent.hargaTiket.toString(),
            onValueChange = { newValue ->
                val harga = newValue.toDoubleOrNull() ?: 0.0
                onValueChange(uiState.insertUiEvent.copy(hargaTiket = harga))
            },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownSelector(
            label = "Pilih Film",
            options = listOf("Film A", "Film B"), // Placeholder, ubah dengan data film nyata
            selectedOption = "Film A", // Placeholder, ubah dengan nilai nyata dari UI State
            onOptionSelected = { selected ->
                // Lakukan sesuatu berdasarkan pilihan film
                // Contoh: ubah idFilm berdasarkan nama yang dipilih
                onValueChange(uiState.insertUiEvent.copy(idFilm = selected.hashCode()))
            }
        )

        DropdownSelector(
            label = "Pilih Studio",
            options = listOf("Studio 1", "Studio 2"), // Placeholder, ubah dengan data studio nyata
            selectedOption = "Studio 1", // Placeholder
            onOptionSelected = { selected ->
                // Lakukan sesuatu berdasarkan pilihan studio
                onValueChange(uiState.insertUiEvent.copy(idStudio = selected.hashCode()))
            }
        )

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Simpan")
        }
    }
}
