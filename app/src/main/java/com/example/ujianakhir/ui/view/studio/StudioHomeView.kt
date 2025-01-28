package com.example.ujianakhir.ui.view.studio

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.studio.HomeStudioViewModel
import com.example.ujianakhir.ui.viewmodel.studio.HomeStudioUiState
import com.example.ujianakhir.R
import com.example.ujianakhir.model.Studio
import com.example.ujianakhir.ui.customwidget.TopAppBar



object DestinasiHomeStudio : DestinasiNavigasi {
    override val route = "home_studio"
    override val titleRes = "Daftar Studio"
    val descriptionRes = "Temukan Studio Anda Disini"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeStudioView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    navigateToEditStudio: (Int) -> Unit,
    viewModel: HomeStudioViewModel = viewModel()
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = DestinasiHomeStudio.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiHomeStudio.descriptionRes,
                onRefresh = { viewModel.getAllStudios() },
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Studio")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.studioUiState,
            retryAction = { viewModel.getAllStudios() },
            modifier = modifier.padding(innerPadding),
            onStudioClick = navigateToEditStudio,
            onDeleteClick = { idStudio -> viewModel.deleteStudio(idStudio) }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeStudioUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onStudioClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeStudioUiState.Loading -> LoadingScreen(modifier = modifier)
        is HomeStudioUiState.Success -> StudioList(
            studio = homeUiState.studios,
            modifier = modifier,
            onDeleteClick = onDeleteClick,
            onStudioClick = onStudioClick
        )
        is HomeStudioUiState.Error -> ErrorScreen(retryAction, modifier = modifier)
    }
}

@Composable
fun StudioList(
    studio: List<Studio>,
    modifier: Modifier = Modifier,
    onStudioClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(studio) { studioItem ->
            StudioCard(
                studioItem = studioItem,
                onEditClick = { onStudioClick(studioItem.idStudio) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun StudioCard(
    studioItem: Studio,
    onDeleteClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.studio),
                contentDescription = studioItem.namaStudio,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = studioItem.namaStudio,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = studioItem.kapasitas,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.videoeditor),
                contentDescription = "Edit Studio",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onEditClick(studioItem.idStudio) }
            )
            Icon(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Hapus Studio",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onDeleteClick(studioItem.idStudio) }
            )
        }
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
