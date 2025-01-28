package com.example.ujianakhir.ui.view.studio

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.studio.StudioDetailUiState
import com.example.ujianakhir.ui.viewmodel.studio.StudioDetailViewModel
import com.example.ujianakhir.ui.customwidget.TopAppBar


object DestinasiDetailPelanggan : DestinasiNavigasi {
    override val route = "pelanggan_detail"
    override val titleRes = "Detail Studio"
    val descriptionRes = "Deskripsi Studio"
    const val IDSTUDIO = "idStudio"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPelangganScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudioDetailViewModel = viewModel()
) {
    val uiState = viewModel.studioDetailUiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailPelanggan.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                description = DestinasiDetailPelanggan.descriptionRes
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is StudioDetailUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is StudioDetailUiState.Error -> Text(
                        text = "Gagal Memuat Data Studio.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is StudioDetailUiState.Success -> {
                        val studio = uiState.studio

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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Detail Studio",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = "Nama Studio: ${studio.namaStudio}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Kapasitas: ${studio.kapasitas} orang",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    )
}
