package com.example.ujianakhir.ui.view.film

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.R
import com.example.ujianakhir.model.Film
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.viewmodel.HomeFilmUiState
import com.example.ujianakhir.viewmodel.HomeFilmViewModel
import com.example.ujianakhir.ui.customwidget.TopAppBar


object DestinasiHomeFilm : DestinasiNavigasi {
    override val route = "home_film"
    override val titleRes = "Daftar Film"
    val descriptionRes = "Temukan Film Favorit Anda"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFilmView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onFilmClick: (Int) -> Unit,
    navigateToItemEntry: () -> Unit,
    viewModel: HomeFilmViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = DestinasiHomeFilm.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiHomeFilm.descriptionRes,
                onRefresh = { viewModel.getAllFilms() },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Film")
            }
        },
    ) { innerPadding ->
        HomeFilmStatus(
            homeUiState = viewModel.filmUiState,
            retryAction = { viewModel.getAllFilms() },
            modifier = modifier.padding(innerPadding),
            onFilmClick = onFilmClick,
            onDeleteClick = { idFilm -> viewModel.deleteFilm(idFilm) },
        )
    }
}

@Composable
fun HomeFilmStatus(
    homeUiState: HomeFilmUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onFilmClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeFilmUiState.Loading -> LoadingScreen(modifier = modifier)
        is HomeFilmUiState.Success -> FilmsList(
            films = homeUiState.films,
            modifier = modifier,
            onDeleteClick = onDeleteClick,
            onFilmClick = onFilmClick
        )
        is HomeFilmUiState.Error -> ErrorScreen(retryAction, modifier = modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.loading),
                contentDescription = stringResource(id = R.string.loading)
            )
            Text("Memuat data...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.error404),
                contentDescription = "Error"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.loading_failed),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = retryAction) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun FilmsList(
    films: List<Film>,
    modifier: Modifier = Modifier,
    onFilmClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(films) { filmItem ->
            FilmCard(
                filmItem = filmItem,
                onEditClick = { onFilmClick(filmItem.idFilm) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun FilmCard(
    filmItem: Film,
    onDeleteClick: (Int) -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEditClick)
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.horrormovie),
                contentDescription = filmItem.judulFilm,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = filmItem.judulFilm,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Durasi: ${filmItem.durasi} menit",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Genre: ${filmItem.genre}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Hapus Film",
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 8.dp)
                    .clickable { onDeleteClick(filmItem.idFilm) }
            )
        }
    }
}
