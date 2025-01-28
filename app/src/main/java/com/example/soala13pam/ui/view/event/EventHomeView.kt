package com.example.soala13pam.ui.view.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soala13pam.model.Event
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.event.EventHomeViewModel
import com.example.soala13pam.ui.viewmodel.event.EventPenyediaViewModel
import com.example.soala13pam.ui.viewmodel.event.EventUiState

object DestinasiEventHome : DestinasiNavigasi {
    override val route = "Event Home"
    override val titleRes = "Daftar Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventHomeScreen(
    navigateToEventEntry: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: EventHomeViewModel = viewModel(factory = EventPenyediaViewModel.Factory)
) {
    val validasiDelete = remember { mutableStateOf(false) }
    val deleteEvent = remember { mutableStateOf<Event?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Event") },
                actions = {
                    IconButton(onClick = { viewModel.getEvent() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    Button(onClick = { onBackButtonClicked() }) {
                        Text(text = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEventEntry,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Event")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        EventStatus(
            eventUiState = viewModel.eventUiState,
            retryAction = { viewModel.getEvent() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                deleteEvent.value = it
                validasiDelete.value = true
            }
        )

        if (validasiDelete.value) {
            AlertDialog(
                onDismissRequest = { validasiDelete.value = false },
                text = { Text("Apakah Anda yakin ingin menghapus event ini?") },
                confirmButton = {
                    Button(onClick = {
                        deleteEvent.value?.let { event ->
                            viewModel.deleteEvent(event.idEvent)
                        }
                        validasiDelete.value = false
                    }) {
                        Text("Hapus")
                    }
                },
                dismissButton = {
                    Button(onClick = { validasiDelete.value = false }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}

@Composable
fun EventStatus(
    eventUiState: EventUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (eventUiState) {
        is EventUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is EventUiState.Success -> {
            if (eventUiState.event.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data event")
                }
            } else {
                EventList(
                    event = eventUiState.event,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idEvent) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is EventUiState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Gagal memuat data")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = retryAction) {
                    Text("Coba Lagi")
                }
            }
        }
    }
}

@Composable
fun EventList(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onDetailClick: (Event) -> Unit,
    onDeleteClick: (Event) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(event) { event ->
            EventCard(
                event = event,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(event) },
                onDeleteClick = { onDeleteClick(event) }
            )
        }
    }
}
//error loading

@Composable
fun EventCard(
    event: Event,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = "EVENT")
                Spacer(modifier = Modifier.width(6.dp))
                Text(event.namaEvent, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(event) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
            Text("ID EVENT: ${event.idEvent}", style = MaterialTheme.typography.bodyMedium)
            Text("Tanggal: ${event.tanggalEvent}", style = MaterialTheme.typography.bodyMedium)
            Text("Lokasi: ${event.lokasiEvent}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}