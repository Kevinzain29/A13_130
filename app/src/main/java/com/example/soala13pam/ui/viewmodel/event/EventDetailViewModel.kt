package com.example.soala13pam.ui.viewmodel.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Event
import com.example.soala13pam.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class EventDetailUiState {
    data class Success(val event: Event) : EventDetailUiState()
    object Error : EventDetailUiState()
    object Loading : EventDetailUiState()
}

class EventDetailViewModel(private val repository: EventRepository) : ViewModel() {
    private val _detailUiStateE = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val detailUiStateE: StateFlow<EventDetailUiState> = _detailUiStateE

    fun getEventById(idEvent: String) {
        viewModelScope.launch {
            _detailUiStateE.value = EventDetailUiState.Loading
            try {
                val event = repository.getEventById(idEvent)
                _detailUiStateE.value = EventDetailUiState.Success(event)
            } catch (e: IOException) {
                e.printStackTrace()
                _detailUiStateE.value = EventDetailUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _detailUiStateE.value = EventDetailUiState.Error
            }
        }
    }
}