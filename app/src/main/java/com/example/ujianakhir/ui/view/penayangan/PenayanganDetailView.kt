package com.example.ujianakhir.ui.view.penayangan

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganDetailUiState
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganDetailViewModel

object DestinasiDetailPenayangan : DestinasiNavigasi {
    override val route = "penayangan_detail"
    override val titleRes = "Detail Penayangan"
    val descriptionRes = "Deskripsi Penayangan"
    const val idPenayangan = "idPenayangan"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenayanganScreen(
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PenayanganDetailViewModel = viewModel()
) {
    val uiState = viewModel.penayanganDetailUiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailPenayangan.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                description = DestinasiDetailPenayangan.descriptionRes
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is PenayanganDetailUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is PenayanganDetailUiState.Error -> Text(
                        text = "Gagal Memuat Data Penayangan.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is PenayanganDetailUiState.Success -> {
                        val penayangan = uiState.penayangan

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE3F2FD)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Detail Penayangan",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = "Judul Film: ${penayangan.idFilm}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "ID Studio: ${penayangan.idStudio}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Tanggal Penayangan: ${penayangan.tanggalPenayangan}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Harga Tiket: Rp ${penayangan.hargaTiket}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = { onEditClick(penayangan.idPenayangan) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF2196F3),
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text(text = "Edit")
                                }
                                Button(
                                    onClick = { onDeleteClick(penayangan.idPenayangan) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF2196F3),
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text(text = "Hapus")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
