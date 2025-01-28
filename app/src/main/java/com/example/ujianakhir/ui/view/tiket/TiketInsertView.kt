package com.example.ujianakhir.ui.view.tiket

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.tiket.InsertTiketUiEvent
import com.example.ujianakhir.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.ujianakhir.ui.viewmodel.tiket.InsertTiketUiState
import com.example.ujianakhir.ui.customwidget.TopAppBar
import kotlinx.coroutines.launch

object DestinasiInsertTiket : DestinasiNavigasi {
    override val route = "insert_tiket"
    override val titleRes = "Insert Tiket"
    val descriptionRes = "Masukkan Detail Tiket untuk Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTiketView(
    navigateBack: () -> Unit,
    viewModel: InsertTiketViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = DestinasiInsertTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiInsertTiket.descriptionRes,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            InsertTiketBody(
                insertTiketUiState = viewModel.uiTiketState,
                tiketList = viewModel.tiketList,
                onValueChange = viewModel::updateInsertTiketState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertTiket()
                        snackbarHostState.showSnackbar("Tiket berhasil disimpan!")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun InsertTiketBody(
    insertTiketUiState: InsertTiketUiState,
    tiketList: List<Tiket>,
    onValueChange: InsertTiketUiEvent.() -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TiketForm(
            uiEvent = insertTiketUiState.insertTiketUiEvent,
            tiketList = tiketList,
            onValueChange = onValueChange
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Simpan", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketForm(
    uiEvent: InsertTiketUiEvent,
    tiketList: List<Tiket>,
    onValueChange: InsertTiketUiEvent.() -> Unit
) {
    var selectedTiketLabel by remember { mutableStateOf("Pilih Tiket") }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dropdown Tiket
        ExposedDropdownMenuBox(
            expanded = uiEvent.idPenayangan > 0,
            onExpandedChange = { selectedTiketLabel = "Pilih Tiket" }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedTiketLabel,
                onValueChange = {},
                label = { Text("Tiket") },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = uiEvent.idPenayangan > 0,
                onDismissRequest = { selectedTiketLabel = "Pilih Tiket" }
            ) {
                tiketList.forEach { tiket ->
                    DropdownMenuItem(
                        text = { Text("ID Tiket: ${tiket.idTiket}") },
                        onClick = {
                            selectedTiketLabel = "ID Tiket: ${tiket.idTiket}"
                            uiEvent.copy(idPenayangan = tiket.idPenayangan).onValueChange()
                        }
                    )
                }
            }
        }

        // Input Jumlah Tiket
        OutlinedTextField(
            value = uiEvent.jumlahTiket.toString(),
            onValueChange = { onValueChange(uiEvent.copy(jumlahTiket = it.toIntOrNull() ?: 0)) },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input Total Harga
        OutlinedTextField(
            value = uiEvent.totalHarga.toString(),
            onValueChange = { onValueChange(uiEvent.copy(totalHarga = it.toDoubleOrNull() ?: 0.0)) },
            label = { Text("Total Harga") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input Status Pembayaran
        OutlinedTextField(
            value = uiEvent.statusPembayaran,
            onValueChange = { onValueChange(uiEvent.copy(statusPembayaran = it)) },
            label = { Text("Status Pembayaran") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
