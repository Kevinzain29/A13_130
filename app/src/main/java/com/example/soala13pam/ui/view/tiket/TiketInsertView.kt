package com.example.soala13pam.ui.view.tiket

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
import com.example.soala13pam.model.Event
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.ui.customwidget.SelectedTextField
import com.example.soala13pam.ui.customwidget.TopAppBar
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.tiket.TiketInsertUiEvent
import com.example.soala13pam.ui.viewmodel.tiket.TiketInsertUiState
import com.example.soala13pam.ui.viewmodel.tiket.TiketInsertViewModel
import com.example.soala13pam.ui.viewmodel.tiket.TiketPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "item_entry_tiket"
    override val titleRes = "Entry Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TiketInsertViewModel = viewModel(factory = TiketPenyediaViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listEvent = viewModel.listEvent
    val listPeserta = viewModel.listPeserta

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBodyTiket(
            tiketInsertUiState = viewModel.uiState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            listEvent = listEvent,  // Kirim daftar event
            listPeserta = listPeserta,  // Kirim daftar peserta
            isLoading = viewModel.isLoading // Kirim status loading
        )
    }
}

@Composable
fun EntryBodyTiket(
    tiketInsertUiState: TiketInsertUiState,
    onTiketValueChange: (TiketInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    listEvent: List<Event>,  // Tambahkan parameter listEvent
    listPeserta: List<Peserta>,  // Tambahkan parameter listPeserta
    isLoading: Boolean // Tambahkan parameter isLoading
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            FormInputTiket(
                tiketInsertUiEvent = tiketInsertUiState.insertUiEvent3,
                onValueChange = onTiketValueChange,
                modifier = Modifier.fillMaxWidth(),
                listEvent = listEvent,  // Kirim daftar event
                listPeserta = listPeserta,  // Kirim daftar peserta
            )
            Button(
                onClick = onSaveClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Simpan")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTiket(
    tiketInsertUiEvent: TiketInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (TiketInsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    listEvent: List<Event>,  // Tambahkan daftar event
    listPeserta: List<Peserta>,  // Tambahkan daftar peserta
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = tiketInsertUiEvent.idTiket,
            onValueChange = { onValueChange(tiketInsertUiEvent.copy(idTiket = it)) },
            label = { Text("ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        SelectedTextField(
            options = listEvent.map { it.namaEvent },  // Menampilkan namaEvent
            selectedValue = tiketInsertUiEvent.idEvent,  // Menyimpan ID event yang dipilih
            label = "EVENT",
            onValueChangedEvent = { namaEvent ->
                // Temukan ID berdasarkan namaEvent yang dipilih
                val eventId = listEvent.find { it.namaEvent == namaEvent }?.idEvent
                eventId?.let {
                    onValueChange(tiketInsertUiEvent.copy(idEvent = it))  // Simpan ID
                }
            }
        )
        SelectedTextField(
            options = listPeserta.map { it.namaPeserta },  // Menampilkan namaPeserta
            selectedValue = tiketInsertUiEvent.idPeserta,  // Menyimpan ID peserta yang dipilih
            label = "PESERTA",
            onValueChangedEvent = { namaPeserta ->
                // Temukan ID berdasarkan namaPeserta yang dipilih
                val pesertaId = listPeserta.find { it.namaPeserta == namaPeserta }?.idPeserta
                pesertaId?.let {
                    onValueChange(tiketInsertUiEvent.copy(idPeserta = it))  // Simpan ID
                }
            }
        )
        OutlinedTextField(
            value = tiketInsertUiEvent.kapasitasTiket,
            onValueChange = { onValueChange(tiketInsertUiEvent.copy(kapasitasTiket = it)) },
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = tiketInsertUiEvent.hargaTiket,
            onValueChange = { onValueChange(tiketInsertUiEvent.copy(hargaTiket = it)) },
            label = { Text("Harga Tiket") },
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