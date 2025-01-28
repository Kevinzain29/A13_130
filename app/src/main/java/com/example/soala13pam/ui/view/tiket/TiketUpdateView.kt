package com.example.soala13pam.ui.view.tiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.soala13pam.ui.customwidget.TopAppBar
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.tiket.TiketPenyediaViewModel
import com.example.soala13pam.ui.viewmodel.tiket.TiketUpdateUiEvent
import com.example.soala13pam.ui.viewmodel.tiket.TiketUpdateUiState
import com.example.soala13pam.ui.viewmodel.tiket.TiketUpdateViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTiket : DestinasiNavigasi {
    override val route = "item_update_tiket"
    override val titleRes = "update tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketUpdateView(
    idTiket: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TiketUpdateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = TiketPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idTiket) {
        viewModel.loadTiket(idTiket)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyTiket(
            tiketUpdateUiState = viewModel.uiState,
            onTiketValueChange = viewModel::updateTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket(idTiket)
                    navigateBack()
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
fun UpdateBodyTiket(
    tiketUpdateUiState: TiketUpdateUiState,
    onTiketValueChange: (TiketUpdateUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTiket(
            tiketUpdateUiEvent = tiketUpdateUiState.tiketUpdateUiEvent,
            onValueChange = onTiketValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTiket(
    tiketUpdateUiEvent: TiketUpdateUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (TiketUpdateUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = tiketUpdateUiEvent.idTiket,
            onValueChange = { onValueChange(tiketUpdateUiEvent.copy(idTiket = it)) },
            label = { Text("id Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = tiketUpdateUiEvent.idEvent,
            onValueChange = { onValueChange(tiketUpdateUiEvent.copy(idEvent = it)) },
            label = { Text("id Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = tiketUpdateUiEvent.idPeserta,
            onValueChange = { onValueChange(tiketUpdateUiEvent.copy(idPeserta = it)) },
            label = { Text("id Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = tiketUpdateUiEvent.kapasitasTiket,
            onValueChange = { onValueChange(tiketUpdateUiEvent.copy(kapasitasTiket = it)) },
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = tiketUpdateUiEvent.hargaTiket,
            onValueChange = { onValueChange(tiketUpdateUiEvent.copy(hargaTiket = it)) },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Data",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}