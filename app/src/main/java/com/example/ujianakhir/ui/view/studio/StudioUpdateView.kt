package com.example.ujianakhir.ui.view.studio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.navigation.DestinasiNavigasi
import com.example.ujianakhir.viewmodel.StudioUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import com.example.ujianakhir.ui.customwidget.TopAppBar
import com.example.ujianakhir.ui.view.studio.EntryStudioBody

object DestinasiUpdateStudio : DestinasiNavigasi {
    override val route = "update_studio"
    override val titleRes = "Update Studio"
    val descriptionRes = "Perbaharui data studio"
    const val idStudio = "idStudio"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStudioScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: StudioUpdateViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateStudio.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                description = DestinasiUpdateStudio.descriptionRes,
                navigateUp = onBack
            )
        }
    ) { padding ->
        EntryStudioBody(
            modifier = Modifier.padding(padding),
            uiStateStudio = viewModel.uiStateUpdate,
            onStudioValueChange = viewModel::updateInsertStudioState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateStudio()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }

                }
            }
        )
    }
}