package com.example.ujianakhir.ui.view.tiket

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.tiket.TiketUiState
import com.example.ujianakhir.ui.viewmodel.tiket.TiketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material3.Text


object DestinasiDetailTiket : DestinasiNavigasi {
    override val route = "tiket_detail"
    override val titleRes = "Update Tiket"
    val descriptionRes = "Perbarui data Tiket"
    const val IDTIKET = "idTiket"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: TiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.loadTiketById() // Ensure the data is loaded when the screen is first displayed
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiDetailTiket.descriptionRes,
                navigateUp = onBack
            )
        }
    ) { padding ->
        InsertTiketBody(
            modifier = Modifier.padding(padding),
            tiketState = viewModel.tiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket(viewModel.tiketState.tiket)
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
fun InsertTiketBody(
    tiketState: TiketUiState,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (tiketState) {
        is TiketUiState.Loading -> {
            // Display a loading indicator
        }
        is TiketUiState.Success -> {
            // Display Tiket form with existing data
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Display Tiket fields, pre-filled with existing data
                OutlinedTextField(
                    value = tiketState.tiket.idPenayangan.toString(),
                    onValueChange = {},
                    label = { Text("ID Penayangan") },
                    readOnly = true
                )

                OutlinedTextField(
                    value = tiketState.tiket.jumlahTiket.toString(),
                    onValueChange = { /* Handle change */ },
                    label = { Text("Jumlah Tiket") }
                )

                OutlinedTextField(
                    value = tiketState.tiket.totalHarga.toString(),
                    onValueChange = { /* Handle change */ },
                    label = { Text("Total Harga") }
                )

                OutlinedTextField(
                    value = tiketState.tiket.statusPembayaran,
                    onValueChange = { /* Handle change */ },
                    label = { Text("Status Pembayaran") }
                )

                // Save button
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
        is TiketUiState.Error -> {
            // Show error message
            Text(text = "Error loading Tiket data", color = Color.Red)
        }
    }
}
