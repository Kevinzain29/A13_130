package com.example.soala13pam.ui.view.tiket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soala13pam.ui.customwidget.TopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.tiket.TiketDetailUiState
import com.example.soala13pam.ui.viewmodel.tiket.TiketDetailViewModel
import com.example.soala13pam.ui.viewmodel.tiket.TiketPenyediaViewModel

object DestinasiTiketDetail : DestinasiNavigasi {
    override val route = "tiket_detail"
    override val titleRes = "Detail tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketDetailView(
    idTiket: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: TiketDetailViewModel = viewModel(factory = TiketPenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiStateT.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idTiket) {
        viewModel.getTiketById(idTiket)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiTiketDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTiketById(idTiket)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Tiket")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is TiketDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is TiketDetailUiState.Success -> {
                val tiket = (uiState as TiketDetailUiState.Success).tiket
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailTiket(tiket = tiket)
                }
            }
            is TiketDetailUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Gagal memuat data")
                }
            }
        }
    }
}

@Composable
fun ItemDetailTiket(
    modifier: Modifier = Modifier,
    tiket: Tiket
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailTiket(judul = "idTiket", isinya = tiket.idTiket)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTiket(judul = "idEvent", isinya = tiket.idEvent)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTiket(judul = "idPeserta", isinya = tiket.idPeserta)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTiket(judul = "kapasitasTiket", isinya = tiket.kapasitasTiket)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTiket(judul = "hargaTiket", isinya = tiket.hargaTiket)
            Spacer(modifier = Modifier.height(6.dp))

            tiket.tanggalEvent?.let { ComponentDetailTiket(judul = "tanggalEvent", isinya = it) }
            Spacer(modifier = Modifier.height(6.dp))

            tiket.lokasiEvent?.let { ComponentDetailTiket(judul = "lokasiEvent", isinya = it) }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun ComponentDetailTiket(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
    }
}