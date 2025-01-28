package com.example.soala13pam.ui.view.transaksi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soala13pam.model.Transaksi
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiFULL
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiHomeViewModel
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiPenyediaViewModel
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiUiState

object DestinasiTransaksiHome : DestinasiNavigasi {
    override val route = "Transaksi Home"
    override val titleRes = "Daftar Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaksiHomeScreen(
    navigateToTransaksiEntry: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: TransaksiHomeViewModel = viewModel(factory = TransaksiPenyediaViewModel.Factory)
) {
    val validasiDelete = remember { mutableStateOf(false) }
    val deleteTransaksi = remember { mutableStateOf<Transaksi?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Transaksi") },
                actions = {
                    IconButton(onClick = { viewModel.getTransaksi() }) {
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
                onClick = navigateToTransaksiEntry,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Transaksi")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        TransaksiStatus(
            transaksiUiState = viewModel.transaksiUiState,
            retryAction = { viewModel.getTransaksi() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                deleteTransaksi.value = it
                validasiDelete.value = true
            }
        )

        if (validasiDelete.value) {
            AlertDialog(
                onDismissRequest = { validasiDelete.value = false },
                text = { Text("Apakah Anda yakin ingin menghapus transaksi ini?") },
                confirmButton = {
                    Button(onClick = {
                        deleteTransaksi.value?.let { transaksi ->
                            viewModel.deleteTransaksi(transaksi.idTransaksi)
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
fun TransaksiStatus(
    transaksiUiState: TransaksiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (transaksiUiState) {
        is TransaksiUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is TransaksiUiState.Success -> {
            if (transaksiUiState.transaksi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data transaksi")
                }
            } else {
                TransaksiList(
                    transaksi = transaksiUiState.transaksi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idTransaksi) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is TransaksiUiState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
fun TransaksiList(
    transaksi: List<TransaksiFULL>,
    modifier: Modifier = Modifier,
    onDetailClick: (Transaksi) -> Unit,
    onDeleteClick: (Transaksi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(transaksi) { transaksi ->
            TransaksiCard(
                transaksi = transaksi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(transaksi.transaksi) },
                onDeleteClick = { onDeleteClick(transaksi.transaksi) }
            )
        }
    }
}

@Composable
fun TransaksiCard(
    transaksi: TransaksiFULL,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {}
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
                Icon(Icons.Filled.Favorite, contentDescription = "TRANSAKSI")
                Spacer(modifier = Modifier.width(6.dp))
                Text(transaksi.transaksi.idTransaksi, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(transaksi.transaksi) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
            Text("Tanggal Transaksi: ${transaksi.transaksi.tanggalTransaksi}", style = MaterialTheme.typography.bodyMedium)
            Text("Jumlah Pembayaran: ${transaksi.transaksi.jumlahPembayaran}", style = MaterialTheme.typography.bodyMedium)
            Text("ID Tiket: ${transaksi.transaksi.idTiket}", style = MaterialTheme.typography.bodyMedium)
            Text("Nama Event: ${transaksi.event?.namaEvent}", style = MaterialTheme.typography.bodyMedium)
            Text("Nama Peserta: ${transaksi.peserta?.namaPeserta}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}