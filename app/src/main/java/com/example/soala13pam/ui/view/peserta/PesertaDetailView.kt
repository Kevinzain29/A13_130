package com.example.soala13pam.ui.view.peserta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.peserta.PesertaDetailUiState
import com.example.soala13pam.ui.viewmodel.peserta.PesertaDetailViewModel
import com.example.soala13pam.ui.viewmodel.peserta.PesertaPenyediaViewModel

object DestinasiPesertaDetail : DestinasiNavigasi {
    override val route = "peserta_detail"
    override val titleRes = "Detail Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesertaDetailView(
    idPeserta: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: PesertaDetailViewModel = viewModel(factory = PesertaPenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getPesertaById(idPeserta)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiPesertaDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPesertaById(idPeserta)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Peserta")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is PesertaDetailUiState.Loading -> {
                Text("Loading", Modifier.fillMaxSize())
            }
            is PesertaDetailUiState.Success -> {
                val peserta = (uiState as PesertaDetailUiState.Success).peserta
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailPeserta(peserta = peserta)
                }
            }
            is PesertaDetailUiState.Error -> {
                Text(
                    text = "Gagal memuat data",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun ItemDetailPeserta(
    modifier: Modifier = Modifier,
    peserta: Peserta
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
            ComponentDetailPeserta(judul = "ID", isinya = peserta.idPeserta)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailPeserta(judul = "Nama", isinya = peserta.namaPeserta)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailPeserta(judul = "Email", isinya = peserta.email)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailPeserta(judul = "NomorTelepon", isinya = peserta.nomorTelepon)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun ComponentDetailPeserta(
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