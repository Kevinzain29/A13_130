package com.example.soala13pam.ui.viewmodel.tiket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.repository.EventRepository
import com.example.soala13pam.repository.TiketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class TiketDetailUiState {
    data class Success(val tiket: Tiket) : TiketDetailUiState()
    object Error : TiketDetailUiState()
    object Loading : TiketDetailUiState()
}

class TiketDetailViewModel(
    private val repository: TiketRepository,
    private val eventRepo: EventRepository
) : ViewModel() {
    private val _detailUiStateT = MutableStateFlow<TiketDetailUiState>(TiketDetailUiState.Loading)
    val detailUiStateT: StateFlow<TiketDetailUiState> = _detailUiStateT

    fun getTiketById(idTiket: String) {
        viewModelScope.launch {
            _detailUiStateT.value = TiketDetailUiState.Loading
            try {
                val tiket = repository.getTiketById(idTiket)
                val event = eventRepo.getEventById(tiket.idEvent)
                val tiketDetail = tiket.copy(
                    tanggalEvent = event?.tanggalEvent ?: "Tidak Diketahui",
                    lokasiEvent = event?.lokasiEvent ?: "Tidak Diketahui"
                )
                _detailUiStateT.value = TiketDetailUiState.Success(tiketDetail)
            } catch (e: IOException) {
                _detailUiStateT.value = TiketDetailUiState.Error
            } catch (e: HttpException) {
                _detailUiStateT.value = TiketDetailUiState.Error
            }
        }
    }
}