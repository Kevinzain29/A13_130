package com.example.soala13pam.ui.view.transaksi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiDetailUiState
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiDetailViewModel
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiFULL
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiPenyediaViewModel

object DestinasiTransaksiDetail : DestinasiNavigasi {
    override val route = "transaksi_detail"
    override val titleRes = "Detail transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaksiDetailView(
    idTransaksi: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: TransaksiDetailViewModel = viewModel(factory = TransaksiPenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiStateT.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getTransaksiById(idTransaksi)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiTransaksiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTransaksiById(idTransaksi)
                }
            )
        },

    ) { innerPadding ->
        when (uiState) {
            is TransaksiDetailUiState.Loading -> {
                Text("Loading", Modifier.fillMaxSize())
            }
            is TransaksiDetailUiState.Success -> {
                val transaksi = (uiState as TransaksiDetailUiState.Success).transaksi
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailTransaksi(transaksi = transaksi)
                }
            }
            is TransaksiDetailUiState.Error -> {
                Text(
                    text = "Gagal memuat data",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun ItemDetailTransaksi(
    modifier: Modifier = Modifier,
    transaksi: TransaksiFULL
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
            ComponentDetailTransaksi(judul = "idTransaksi", isinya = transaksi.transaksi.idTransaksi)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTransaksi(judul = "idTiket", isinya = transaksi.transaksi.idTiket)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTransaksi(judul = "tanggalTransaksi", isinya = transaksi.transaksi.tanggalTransaksi)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTransaksi(judul = "jumlahPembayaran", isinya = transaksi.transaksi.jumlahPembayaran)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTransaksi(judul = "jumlahTiket", isinya = transaksi.transaksi.jumlahTiket)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTransaksi(judul = "Nama Event", isinya = transaksi.event?.namaEvent?: "")
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTransaksi(judul = "Nama Event", isinya = transaksi.peserta?.namaPeserta?: "")
        }
    }
}

@Composable
fun ComponentDetailTransaksi(
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
