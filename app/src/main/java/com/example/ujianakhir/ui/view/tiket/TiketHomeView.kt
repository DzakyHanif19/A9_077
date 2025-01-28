package com.example.ujianakhir.ui.view.tiket

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.model.Tiket
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.tiket.HomeTiketUiState
import com.example.ujianakhir.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.ujianakhir.R
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.view.film.ErrorScreen
import com.example.ujianakhir.ui.view.film.LoadingScreen

object DestinasiHomeTiket : DestinasiNavigasi {
    override val route = "home_tiket"
    override val titleRes = "Daftar Tiket"
    val descriptionRes = "Lihat Tiket yang Dipesan oleh Pelanggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTiketView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onTiketClick: (Int) -> Unit,
    navigateToItemEntry: () -> Unit,
    viewModel: HomeTiketViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = DestinasiHomeTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiHomeTiket.descriptionRes,
                onRefresh = { viewModel.getTiket() },
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Tiket")
            }
        }
    ) { innerPadding ->
        HomeTiketStatus(
            homeUiState = viewModel.tiketUiState,
            retryAction = { viewModel.getTiket() },
            modifier = modifier.padding(innerPadding),
            onTiketClick = onTiketClick
        )
    }
}

@Composable
fun HomeTiketStatus(
    homeUiState: HomeTiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onTiketClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeTiketUiState.Loading -> LoadingScreen(modifier = modifier)
        is HomeTiketUiState.Success -> TiketList(
            tiket = homeUiState.tiket,
            modifier = modifier,
            onTiketClick = onTiketClick
        )
        is HomeTiketUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier)
    }
}

@Composable
fun TiketList(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onTiketClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tiket) { tiketItem ->
            TiketCard(
                tiket = tiketItem,
                onTiketClick = onTiketClick
            )
        }
    }
}

@Composable
fun TiketCard(
    tiket: Tiket,
    onTiketClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onTiketClick(tiket.idTiket) })
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.movies),
                contentDescription = "Tiket ${tiket.idTiket}",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Tiket ID: ${tiket.idTiket}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Jumlah: ${tiket.jumlahTiket}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Total Harga: Rp${tiket.totalHarga}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status Pembayaran: ${tiket.statusPembayaran}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
