package com.example.ujianakhir.ui.view.penayangan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganInsertViewModel
import com.example.ujianakhir.ui.viewmodel.penayangan.InsertPenayanganUiEvent
import com.example.ujianakhir.ui.viewmodel.penayangan.InsertPenayanganUiState
import kotlinx.coroutines.launch
import com.example.ujianakhir.ui.customwidget.DatePickerField
import com.example.ujianakhir.ui.customwidget.TopAppBar

object DestinasiInsertPenayangan : DestinasiNavigasi {
    override val route = "insert_penayangan"
    override val titleRes = "Insert Penayangan"
    val descriptionRes = "Masukkan data Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPenayanganView(
    navigateBack: () -> Unit,
    viewModel: PenayanganInsertViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = DestinasiInsertPenayangan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiInsertPenayangan.descriptionRes,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        EntryPenayanganBody(
            insertPenayanganUiState = viewModel.uiState,
            onValueChange = viewModel::updateInsertPenayanganState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenayangan()
                    snackbarHostState.showSnackbar("Data penayangan berhasil disimpan!")
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryPenayanganBody(
    insertPenayanganUiState: InsertPenayanganUiState,
    onValueChange: InsertPenayanganUiEvent.() -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PenayanganForm(
            uiEvent = insertPenayanganUiState.insertUiEvent,
            onValueChange = onValueChange
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun PenayanganForm(
    uiEvent: InsertPenayanganUiEvent,
    onValueChange: InsertPenayanganUiEvent.() -> Unit
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiEvent.idFilm.toString(),
            onValueChange = { onValueChange(uiEvent.copy(idFilm = it.toIntOrNull() ?: 0)) },
            label = { Text("ID Film") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiEvent.idStudio.toString(),
            onValueChange = { onValueChange(uiEvent.copy(idStudio = it.toIntOrNull() ?: 0)) },
            label = { Text("ID Studio") },
            modifier = Modifier.fillMaxWidth()
        )

        var tanggalPenayangan by remember { mutableStateOf(uiEvent.tanggalPenayangan) }
        DatePickerField(
            label = "Tanggal Penayangan",
            selectedDate = tanggalPenayangan,
            onDateSelected = {
                tanggalPenayangan = it
                onValueChange(uiEvent.copy(tanggalPenayangan = it))
            }
        )

        OutlinedTextField(
            value = uiEvent.hargaTiket.toString(),
            onValueChange = { onValueChange(uiEvent.copy(hargaTiket = it.toDoubleOrNull() ?: 0.0)) },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
