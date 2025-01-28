package com.example.ujianakhir.ui.view.film


import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.film.FilmDetailUiState
import com.example.ujianakhir.ui.viewmodel.film.FilmDetailViewModel
import com.example.ujianakhir.ui.view.film.DestinasiDetailFilm
import com.example.ujianakhir.ui.view.film.DestinasiHomeFilm
import androidx.compose.material3.Button
import androidx.compose.material3.Text

object DestinasiDetailFilm : DestinasiNavigasi {
    override val route = "film_detail"
    override val titleRes = "Detail Film"
    val descriptionRes = "Deskripsi Film"
    const val IDFILM = "idFilm"
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailView(
    onFilmClick: (Int) -> Unit,
    onBackClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.filmDetailUiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiHomeFilm.titleRes,
                canNavigateBack = false,
                navigateUp = {},
                description = "Daftar semua film yang tersedia",
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is FilmDetailUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is FilmDetailUiState.Error -> Text(
                        text = "Gagal Memuat Data Film.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is FilmDetailUiState.Success -> {
                        val film = uiState.film
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE3F2FD)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "film: ${film.judulFilm}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Genre: ${film.genre}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Durasi: ${film.durasi} menit",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Rating Usia: ${film.ratingUsia}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Button(
                                        onClick = { onFilmClick(film.idFilm) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF2196F3),
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    ) {
                                        Text(text = "Lihat Detail")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

