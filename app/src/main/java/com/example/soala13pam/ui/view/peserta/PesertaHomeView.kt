package com.example.soala13pam.ui.view.peserta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.peserta.PesertaHomeViewModel
import com.example.soala13pam.ui.viewmodel.peserta.PesertaPenyediaViewModel
import com.example.soala13pam.ui.viewmodel.peserta.PesertaUiState

object DestinasiPesertaHome : DestinasiNavigasi {
    override val route = "Peserta Home"
    override val titleRes = "Daftar Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesertaHomeScreen(
    navigateToPesertaEntry: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PesertaHomeViewModel = viewModel(factory = PesertaPenyediaViewModel.Factory)
) {
    val validasiDelete = remember { mutableStateOf(false) }
    val deletePeserta = remember { mutableStateOf<Peserta?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Peserta") },
                actions = {
                    IconButton(onClick = { viewModel.getPeserta() }) {
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
                onClick = navigateToPesertaEntry,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Peserta")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        PesertaStatus(
            pesertaUiState = viewModel.pesertaUiState,
            retryAction = { viewModel.getPeserta() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                deletePeserta.value = it
                validasiDelete.value = true
            }
        )

        if (validasiDelete.value) {
            AlertDialog(
                onDismissRequest = { validasiDelete.value = false },
                text = { Text("Apakah Anda yakin ingin menghapus peserta ini?") },
                confirmButton = {
                    Button(onClick = {
                        deletePeserta.value?.let { peserta ->
                            viewModel.deletePeserta(peserta.idPeserta)
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
fun PesertaStatus(
    pesertaUiState: PesertaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (pesertaUiState) {
        is PesertaUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is PesertaUiState.Success -> {
            if (pesertaUiState.peserta.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data peserta")
                }
            } else {
                PesertaList(
                    peserta = pesertaUiState.peserta,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPeserta) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is PesertaUiState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
fun PesertaList(
    peserta: List<Peserta>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peserta) -> Unit,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(peserta) { peserta ->
            PesertaCard(
                peserta = peserta,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(peserta) },
                onDeleteClick = { onDeleteClick(peserta) }
            )
        }
    }
}
//error loading

@Composable
fun PesertaCard(
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {}
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
                Icon(Icons.Filled.Person, contentDescription = "Nama Peserta")
                Spacer(modifier = Modifier.width(6.dp))
                Text(peserta.namaPeserta, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(peserta) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
            Text("Email: ${peserta.email}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
