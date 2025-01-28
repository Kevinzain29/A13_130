package com.example.soala13pam.ui.view.event

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
import com.example.soala13pam.model.Event
import com.example.soala13pam.ui.navigation.DestinasiNavigasi
import com.example.soala13pam.ui.viewmodel.event.EventDetailUiState
import com.example.soala13pam.ui.viewmodel.event.EventDetailViewModel
import com.example.soala13pam.ui.viewmodel.event.EventPenyediaViewModel

object DestinasiEventDetail : DestinasiNavigasi {
    override val route = "event_detail"
    override val titleRes = "Detail event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailView(
    idEvent: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: EventDetailViewModel = viewModel(factory = EventPenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiStateE.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getEventById(idEvent)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEventDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getEventById(idEvent)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Event")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is EventDetailUiState.Loading -> {
                Text("Loading", Modifier.fillMaxSize())
            }
            is EventDetailUiState.Success -> {
                val event = (uiState as EventDetailUiState.Success).event
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailEvent(event = event)
                }
            }
            is EventDetailUiState.Error -> {
                Text(
                    text = "Gagal memuat data",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun ItemDetailEvent(
    modifier: Modifier = Modifier,
    event: Event
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
            ComponentDetailEvent(judul = "ID", isinya = event.idEvent)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailEvent(judul = "namaEvent", isinya = event.namaEvent)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailEvent(judul = "deskripsiEvent", isinya = event.deskripsiEvent)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailEvent(judul = "tanggalEvent", isinya = event.tanggalEvent)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailEvent(judul = "lokasiEvent", isinya = event.lokasiEvent)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun ComponentDetailEvent(
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