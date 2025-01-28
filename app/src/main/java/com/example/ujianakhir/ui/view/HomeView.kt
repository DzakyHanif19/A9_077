package com.example.ujianakhir.ui.view

import androidx.compose.material3.Text
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.R
import com.example.ujianakhir.model.Film
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.viewmodel.HomeFilmUiState
import com.example.ujianakhir.viewmodel.HomeFilmViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onFilmDetailClick: (Int) -> Unit,
    navigateToFilmEntry: () -> Unit,
    viewModel: HomeFilmViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = "Selamat Datang",
                canNavigateBack = false,
                description = "Temukan Film Favorit Anda",
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getAllFilms() }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeFilmStatus(
                    filmUiState = viewModel.filmUiState,
                    retryAction = { viewModel.getAllFilms() },
                    modifier = Modifier.fillMaxWidth(),
                    onFilmDetailClick = onFilmDetailClick,
                    onDeleteClick = { idFilm -> viewModel.deleteFilm(idFilm) }
                )
            }
        }
    )
}

@Composable
fun HomeFilmStatus(
    filmUiState: HomeFilmUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onFilmDetailClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    when (filmUiState) {
        is HomeFilmUiState.Loading -> LoadingScreen(modifier = modifier)
        is HomeFilmUiState.Success -> {
            Column {
                Text(
                    text = "Daftar Film",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color(0xFF2197F1)
                )
                FilmList(
                    films = filmUiState.films,
                    modifier = modifier,
                    onDeleteClick = onDeleteClick,
                    onFilmDetailClick = onFilmDetailClick
                )
            }
        }
        is HomeFilmUiState.Error -> ErrorScreen(retryAction, modifier = modifier)
    }
}

@Composable
fun FilmList(
    films: List<Film>,
    modifier: Modifier = Modifier,
    onFilmDetailClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(films) { film ->
            FilmCard(
                film = film,
                onEditClick = { onFilmDetailClick(film.idFilm) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun FilmCard(
    film: Film,
    onDeleteClick: (Int) -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEditClick)
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.horrormovie),
                contentDescription = film.deskripsi,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = film.deskripsi,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = film.genre,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = Color(0xFF2196F3),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Memuat data...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.error404),
                contentDescription = "Error Icon",
                modifier = Modifier.size(64.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Gagal memuat data",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = retryAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                )
            ) {
                Text("Coba Lagi")
            }
        }
    }
}