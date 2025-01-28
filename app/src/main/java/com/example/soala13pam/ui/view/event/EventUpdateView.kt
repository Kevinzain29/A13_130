package com.example.soala13pam.ui.view.event

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
import com.example.soala13pam.ui.viewmodel.event.EventPenyediaViewModel
import com.example.soala13pam.ui.viewmodel.event.EventUpdateUiEvent
import com.example.soala13pam.ui.viewmodel.event.EventUpdateUiState
import com.example.soala13pam.ui.viewmodel.event.EventUpdateViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateEvent : DestinasiNavigasi {
    override val route = "item_update_event"
    override val titleRes = "update event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventUpdateView(
    idEvent: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventUpdateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = EventPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idEvent) {
        viewModel.loadEvent(idEvent)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyEvent(
            eventUpdateUiState = viewModel.uiState,
            onEventValueChange = viewModel::updateEventState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEvent(idEvent)
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
fun UpdateBodyEvent(
    eventUpdateUiState: EventUpdateUiState,
    onEventValueChange: (EventUpdateUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputEvent(
            eventUpdateUiEvent = eventUpdateUiState.eventUpdateUiEvent,
            onValueChange = onEventValueChange,
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
fun FormInputEvent(
    eventUpdateUiEvent: EventUpdateUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (EventUpdateUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = eventUpdateUiEvent.idEvent,
            onValueChange = { onValueChange(eventUpdateUiEvent.copy(idEvent = it)) },
            label = { Text("id Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = eventUpdateUiEvent.namaEvent,
            onValueChange = { onValueChange(eventUpdateUiEvent.copy(namaEvent = it)) },
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = eventUpdateUiEvent.deskripsiEvent,
            onValueChange = { onValueChange(eventUpdateUiEvent.copy(deskripsiEvent = it)) },
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = eventUpdateUiEvent.tanggalEvent,
            onValueChange = { onValueChange(eventUpdateUiEvent.copy(tanggalEvent = it)) },
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = eventUpdateUiEvent.lokasiEvent,
            onValueChange = { onValueChange(eventUpdateUiEvent.copy(lokasiEvent = it)) },
            label = { Text("Lokasi Event") },
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