package com.example.soala13pam.ui.view.transaksi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.model.Transaksi
import com.example.soala13pam.ui.customwidget.SelectedTextField
import com.example.soala13pam.ui.customwidget.TopAppBar
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiInsertUiEvent
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiInsertUiState
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiInsertViewModel
import com.example.soala13pam.ui.viewmodel.transaksi.TransaksiPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTransaksi : DestinasiNavigasi {
    override val route = "item_entry_transaksi"
    override val titleRes = "Entry Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTransaksiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TransaksiInsertViewModel = viewModel(factory = TransaksiPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listTiket = viewModel.listTiket

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEntryTransaksi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBodyTransaksi(
            transaksiInsertUiState = viewModel.uiState,
            onTransaksiValueChange = viewModel::updateInsertTransaksiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTransaksi()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            listTiket = listTiket,
            isLoading = viewModel.isLoading
        )
    }
}

@Composable
fun EntryBodyTransaksi(
    transaksiInsertUiState: TransaksiInsertUiState,
    onTransaksiValueChange: (TransaksiInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    listTiket: List<Tiket>,
    isLoading: Boolean
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            FormInputTransaksi(
                transaksiInsertUiEvent = transaksiInsertUiState.insertUiEvent4,
                onValueChange = onTransaksiValueChange,
                modifier = Modifier.fillMaxWidth(),
                listTiket = listTiket
            )
            Button(
                onClick = onSaveClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ){
                Text(text = "Simpan")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTransaksi(
    transaksiInsertUiEvent: TransaksiInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (TransaksiInsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    listTiket: List<Tiket>
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = transaksiInsertUiEvent.idTransaksi,
            onValueChange = { onValueChange(transaksiInsertUiEvent.copy(idTransaksi = it)) },
            label = { Text("ID TRANSAKSI") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        SelectedTextField(
            options = listTiket.map { it.idTiket },  // Menampilkan namaEvent
            selectedValue = transaksiInsertUiEvent.idTiket,  // Menyimpan ID event yang dipilih
            label = "ID TIKET",
            onValueChangedEvent = {
                    onValueChange(transaksiInsertUiEvent.copy(idTiket = it))
            }
        )
        OutlinedTextField(
            value = transaksiInsertUiEvent.jumlahTiket,
            onValueChange = { onValueChange(transaksiInsertUiEvent.copy(jumlahTiket = it)) },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Menampilkan jumlah pembayaran yang dihitung
        OutlinedTextField(
            value = transaksiInsertUiEvent.jumlahPembayaran,
            onValueChange = { onValueChange(transaksiInsertUiEvent.copy(jumlahPembayaran = it)) },
            label = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false, // Disabled karena akan dihitung otomatis
            singleLine = true
        )
//        OutlinedTextField(
//            value = transaksiInsertUiEvent.jumlahPembayaran,
//            onValueChange = { onValueChange(transaksiInsertUiEvent.copy(jumlahPembayaran = it)) },
//            label = { Text("Jumlah Pembayaran") },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
        OutlinedTextField(
            value = transaksiInsertUiEvent.tanggalTransaksi,
            onValueChange = { onValueChange(transaksiInsertUiEvent.copy(tanggalTransaksi = it)) },
            label = { Text("Tanggal TRANSAKSI") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}