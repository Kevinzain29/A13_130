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
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.input.nestedscroll.nestedScroll
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.soala13pam.ui.customwidget.TopAppBar
    import com.example.soala13pam.ui.navigation.DestinasiNavigasi
    import com.example.soala13pam.ui.viewmodel.event.EventInsertUiEvent
    import com.example.soala13pam.ui.viewmodel.event.EventInsertUiState
    import com.example.soala13pam.ui.viewmodel.event.EventInsertViewModel
    import com.example.soala13pam.ui.viewmodel.event.EventPenyediaViewModel
    import kotlinx.coroutines.launch

    object DestinasiEntryEvent : DestinasiNavigasi {
        override val route = "item_entry_event"
        override val titleRes = "Entry Event"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EntryEventScreen(
        navigateBack: () -> Unit,
        modifier: Modifier = Modifier,
        viewModel: EventInsertViewModel = viewModel(factory = EventPenyediaViewModel.Factory)
    ){
        val coroutineScope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold (
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = DestinasiEntryEvent.titleRes,
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = navigateBack
                )
            }
        ){ innerPadding ->
            EntryBodyEvent(
                eventInsertUiState = viewModel.uiState,
                onEventValueChange = viewModel::updateInsertEventState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertEvent()
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
    fun EntryBodyEvent(
        eventInsertUiState: EventInsertUiState,
        onEventValueChange: (EventInsertUiEvent) -> Unit,
        onSaveClick: () -> Unit,
        modifier: Modifier = Modifier
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = modifier.padding(12.dp)
        ){
            FormInputEvent(
                eventInsertUiEvent = eventInsertUiState.insertUiEvent2,
                onValueChange = onEventValueChange,
                modifier = Modifier.fillMaxWidth()
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FormInputEvent(
        eventInsertUiEvent: EventInsertUiEvent,
        modifier: Modifier = Modifier,
        onValueChange: (EventInsertUiEvent) -> Unit = {},
        enabled: Boolean = true
    ){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            OutlinedTextField(
                value = eventInsertUiEvent.idEvent,
                onValueChange = { onValueChange(eventInsertUiEvent.copy(idEvent = it)) },
                label = { Text("ID EVENT") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = eventInsertUiEvent.namaEvent,
                onValueChange = { onValueChange(eventInsertUiEvent.copy(namaEvent = it)) },
                label = { Text("NAMA EVENT") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = eventInsertUiEvent.deskripsiEvent,
                onValueChange = { onValueChange(eventInsertUiEvent.copy(deskripsiEvent = it)) },
                label = { Text("DESKRIPSI EVENT") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = eventInsertUiEvent.tanggalEvent,
                onValueChange = { onValueChange(eventInsertUiEvent.copy(tanggalEvent = it)) },
                label = { Text("TANGGAL EVENT") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = eventInsertUiEvent.lokasiEvent,
                onValueChange = { onValueChange(eventInsertUiEvent.copy(lokasiEvent = it)) },
                label = { Text("LOKASI EVENT") },
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