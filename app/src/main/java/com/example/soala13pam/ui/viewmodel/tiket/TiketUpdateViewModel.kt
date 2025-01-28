package com.example.soala13pam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.repository.TiketRepository
import kotlinx.coroutines.launch

class TiketUpdateViewModel(private val tiketRepo: TiketRepository) : ViewModel() {
    var uiState by mutableStateOf(TiketUpdateUiState())
        private set
    fun loadTiket(idTiket: String) {
        viewModelScope.launch {
            try {
                val Tiket = tiketRepo.getTiketById(idTiket)
                uiState = TiketUpdateUiState(tiketUpdateUiEvent = Tiket.toUpdateUiEvent3())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateTiketState(tiketUpdateUiEvent: TiketUpdateUiEvent) {
        uiState = TiketUpdateUiState(tiketUpdateUiEvent = tiketUpdateUiEvent)
    }
    suspend fun updateTiket(idTiket: String) {
        viewModelScope.launch {
            try {
                tiketRepo.updateTiket(idTiket, uiState.tiketUpdateUiEvent.toTiket())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class TiketUpdateUiState(
    val tiketUpdateUiEvent: TiketUpdateUiEvent = TiketUpdateUiEvent()
)

data class TiketUpdateUiEvent(
    val idTiket: String = "",
    val idEvent: String = "",
    val idPeserta: String = "",
    val kapasitasTiket: String = "",
    val hargaTiket: String = ""
)

fun TiketUpdateUiEvent.toTiket(): Tiket = Tiket(
    idTiket = idTiket,
    idEvent = idEvent,
    idPeserta = idPeserta,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)

fun Tiket.toUpdateUiEvent3(): TiketUpdateUiEvent = TiketUpdateUiEvent(
    idTiket = idTiket,
    idEvent = idEvent,
    idPeserta = idPeserta,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)