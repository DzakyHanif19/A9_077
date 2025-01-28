package com.example.ujianakhir.ui.view.penayangan

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.model.Penayangan
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.penayangan.HomePenayanganUiState
import com.example.ujianakhir.ui.viewmodel.penayangan.HomePenayanganViewModel
import com.example.ujianakhir.R
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.view.film.ErrorScreen
import com.example.ujianakhir.ui.view.film.LoadingScreen

object DestinasiHomePenayangan : DestinasiNavigasi {
    override val route = "home_penayangan"
    override val titleRes = "Daftar Penayangan"
    val descriptionRes = "Lihat dan Kelola Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenayanganHomeView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onPenayanganClick: (Int) -> Unit,
    navigateToItemEntry: () -> Unit,
    viewModel: HomePenayanganViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = DestinasiHomePenayangan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiHomePenayangan.descriptionRes,
                onRefresh = { viewModel.getAllPenayangan() },
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Penayangan")
            }
        },
    ) { innerPadding ->
        HomePenayanganStatus(
            penayanganUiState = viewModel.penayanganUiState,
            retryAction = { viewModel.getAllPenayangan() },
            modifier = modifier.padding(innerPadding),
            onPenayanganClick = onPenayanganClick,
            onDeleteClick = { idPenayangan -> viewModel.deletePenayangan(idPenayangan) }
        )
    }
}

@Composable
fun HomePenayanganStatus(
    penayanganUiState: HomePenayanganUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onPenayanganClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    when (penayanganUiState) {
        is HomePenayanganUiState.Loading -> LoadingScreen(modifier = modifier)
        is HomePenayanganUiState.Success -> PenayanganList(
            penayangans = penayanganUiState.penayangans,
            modifier = modifier,
            onPenayanganClick = onPenayanganClick,
            onDeleteClick = onDeleteClick
        )
        is HomePenayanganUiState.Error -> ErrorScreen(retryAction, modifier = modifier)
    }
}

@Composable
fun PenayanganList(
    penayangans: List<Penayangan>,
    modifier: Modifier = Modifier,
    onPenayanganClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(penayangans) { penayanganItem ->
            PenayanganCard(
                penayanganItem = penayanganItem,
                onEditClick = { onPenayanganClick(penayanganItem.idPenayangan) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun PenayanganCard(
    penayanganItem: Penayangan,
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
                painter = painterResource(id = R.drawable.clapperboard),
                contentDescription = "Penayangan",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ID: ${penayanganItem.idPenayangan}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "ID Film: ${penayanganItem.idFilm}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "ID Studio: ${penayanganItem.idStudio}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Tanggal: ${penayanganItem.tanggalPenayangan}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Harga Tiket: Rp ${penayanganItem.hargaTiket}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Hapus Penayangan",
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 8.dp)
                    .clickable { onDeleteClick(penayanganItem.idPenayangan) }
            )
        }
    }
}
