package com.example.soala13pam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Event
import com.example.soala13pam.repository.EventRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class EventUiState {
    data class Success(val event: List<Event>) : EventUiState()
    object Error : EventUiState()
    object Loading : EventUiState()
}

class EventHomeViewModel(private val eventRepo: EventRepository) : ViewModel() {
    var eventUiState: EventUiState by mutableStateOf(EventUiState.Loading)
        private set

    init {
        getEvent()
    }

    fun getEvent() {
        viewModelScope.launch {
            eventUiState = EventUiState.Loading
            eventUiState = try {
                EventUiState.Success(eventRepo.getEvent())
            } catch (e: IOException) {
                EventUiState.Error
            } catch (e: HttpException) {
                EventUiState.Error
            }
        }
    }

    fun deleteEvent(idEvent: String) {
        viewModelScope.launch {
            try {
                eventRepo.deleteEvent(idEvent)
                getEvent()
            } catch (e: IOException) {
                EventUiState.Error
            } catch (e: HttpException) {
                EventUiState.Error
            }
        }
    }
}