package com.example.soala13pam.ui.view.tiket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.tiket.TiketHomeViewModel
import com.example.soala13pam.ui.viewmodel.tiket.TiketPenyediaViewModel
import com.example.soala13pam.ui.viewmodel.tiket.TiketUiState

object DestinasiTiketHome : DestinasiNavigasi {
    override val route = "Tiket Home"
    override val titleRes = "Daftar Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketHomeScreen(
    navigateToTiketEntry: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: TiketHomeViewModel = viewModel(factory = TiketPenyediaViewModel.Factory)
) {
    val validasiDelete = remember { mutableStateOf(false) }
    val deleteTiket = remember { mutableStateOf<Tiket?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getTiket()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Tiket") },
                actions = {
                    IconButton(onClick = { viewModel.getTiket() }) {
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
                onClick = navigateToTiketEntry,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Tiket")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        TiketStatus(
            tiketUiState = viewModel.tiketUiState,
            retryAction = { viewModel.getTiket() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                deleteTiket.value = it
                validasiDelete.value = true
            }
        )
        if (validasiDelete.value) {
            AlertDialog(
                onDismissRequest = { validasiDelete.value = false },
                text = { Text("Apakah Anda yakin ingin menghapus tiket ini?") },
                confirmButton = {
                    Button(onClick = {
                        deleteTiket.value?.let { tiket ->
                            viewModel.deleteTiket(tiket.idTiket)
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
fun TiketStatus(
    tiketUiState: TiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tiket) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (tiketUiState) {
        is TiketUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is TiketUiState.Success -> {
            if (tiketUiState.tiket.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data tiket")
                }
            } else {
                TiketList(
                    tiket = tiketUiState.tiket,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idTiket) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is TiketUiState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
fun TiketList(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tiket) -> Unit,
    onDeleteClick: (Tiket) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tiket) { tiket ->
            TiketCard(
                tiket = tiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tiket) },
                onDeleteClick = { onDeleteClick(tiket) }
            )
        }
    }
}

@Composable
fun TiketCard(
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tiket) -> Unit = {}
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
                Icon(Icons.Filled.Email, contentDescription = "TIKET")
                Spacer(modifier = Modifier.width(6.dp))
                Text(tiket.idTiket, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(tiket) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
            Text("ID PESERTA: ${tiket.idPeserta}", style = MaterialTheme.typography.bodyMedium)
            Text("ID EVENT: ${tiket.idEvent}", style = MaterialTheme.typography.bodyMedium)
            Text("Tanggal Event: ${tiket.tanggalEvent}", style = MaterialTheme.typography.bodyMedium)
            Text("Lokasi Event: ${tiket.lokasiEvent}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}