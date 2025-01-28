package com.example.soala13pam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Event
import com.example.soala13pam.repository.EventRepository
import kotlinx.coroutines.launch

class EventUpdateViewModel(private val eventRepo: EventRepository) : ViewModel() {
    var uiState by mutableStateOf(EventUpdateUiState())
        private set
    fun loadEvent(idEvent: String) {
        viewModelScope.launch {
            try {
                val Event = eventRepo.getEventById(idEvent)
                uiState = EventUpdateUiState(eventUpdateUiEvent = Event.toUpdateUiEvent2())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateEventState(eventUpdateUiEvent: EventUpdateUiEvent) {
        uiState = EventUpdateUiState(eventUpdateUiEvent = eventUpdateUiEvent)
    }
    suspend fun updateEvent(idEvent: String) {
        viewModelScope.launch {
            try {
                eventRepo.updateEvent(idEvent, uiState.eventUpdateUiEvent.toEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class EventUpdateUiState(
    val eventUpdateUiEvent: EventUpdateUiEvent = EventUpdateUiEvent()
)

data class EventUpdateUiEvent(
    val idEvent: String = "",
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = ""
)

fun EventUpdateUiEvent.toEvent(): Event = Event(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent
)

fun Event.toUpdateUiEvent2(): EventUpdateUiEvent = EventUpdateUiEvent(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent
)
