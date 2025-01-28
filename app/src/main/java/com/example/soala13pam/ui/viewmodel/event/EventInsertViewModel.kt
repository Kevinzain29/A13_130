package com.example.soala13pam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Event
import com.example.soala13pam.repository.EventRepository
import kotlinx.coroutines.launch

class EventInsertViewModel(private val eventRepo: EventRepository) : ViewModel() {
    var uiState by mutableStateOf(EventInsertUiState())
        private set

    fun updateInsertEventState(insertUiEvent2: EventInsertUiEvent) {
        uiState = EventInsertUiState(insertUiEvent2 = insertUiEvent2)
    }

    fun insertEvent() {
        viewModelScope.launch {
            try {
                eventRepo.insertEvent(uiState.insertUiEvent2.toEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class EventInsertUiState(
    val insertUiEvent2: EventInsertUiEvent = EventInsertUiEvent()
)

data class EventInsertUiEvent(
    val idEvent: String = "",
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = ""
)

fun EventInsertUiEvent.toEvent(): Event = Event(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent
)

fun Event.toUiStateEvent(): EventInsertUiState = EventInsertUiState(
    insertUiEvent2 = toInsertUiEvent2()
)

fun Event.toInsertUiEvent2(): EventInsertUiEvent = EventInsertUiEvent(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent
)