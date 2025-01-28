package com.example.ujianakhir.ui.view.film

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.film.FilmInsertViewModel
import com.example.ujianakhir.ui.viewmodel.film.InsertFilmUiEvent
import com.example.ujianakhir.ui.viewmodel.film.InsertFilmUiState
import com.example.ujianakhir.ui.customwidget.TopAppBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import kotlinx.coroutines.launch

object FilmInsertView : DestinasiNavigasi {
    override val route = "insert_film"
    override val titleRes = "Insert Film"
    val descriptionRes = "Masukkan data Film"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertFilmView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmInsertViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = FilmInsertView.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                description = FilmInsertView.descriptionRes
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        EntryFilmBody(
            insertUiState = viewModel.uiStateFilm,
            onFilmValueChange = viewModel::updateInsertFilmState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertFilm()
                    snackbarHostState.showSnackbar("Film berhasil ditambahkan")
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
fun EntryFilmBody(
    insertUiState: InsertFilmUiState,
    onFilmValueChange: (InsertFilmUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FormInputFilm(
            insertUiEvent = insertUiState.insertFilmUiEvent,
            onValueChange = onFilmValueChange,
            modifier = Modifier.fillMaxWidth()
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
fun FormInputFilm(
    insertUiEvent: InsertFilmUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertFilmUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.judulFilm,
            onValueChange = { onValueChange(insertUiEvent.copy(judulFilm = it)) },
            label = { Text(text = "Judul Film") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.durasi.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(durasi = it.toIntOrNull() ?: 0)) },
            label = { Text(text = "Durasi (menit)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi = it)) },
            label = { Text(text = "Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.genre,
            onValueChange = { onValueChange(insertUiEvent.copy(genre = it)) },
            label = { Text(text = "Genre") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.ratingUsia,
            onValueChange = { onValueChange(insertUiEvent.copy(ratingUsia = it)) },
            label = { Text(text = "Rating Usia") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Semua data harus terisi!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
