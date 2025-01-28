package com.example.soala13pam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.repository.EventRepository
import com.example.soala13pam.repository.TiketRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class TiketUiState {
    data class Success(val tiket: List<Tiket>) : TiketUiState()
    object Error : TiketUiState()
    object Loading : TiketUiState()
}

class TiketHomeViewModel(
    private val tiketRepo: TiketRepository,
    private val eventRepo: EventRepository
) : ViewModel() {
    var tiketUiState: TiketUiState by mutableStateOf(TiketUiState.Loading)
        private set

    init {
        getTiket()
    }

    fun getTiket() {
        viewModelScope.launch {
            tiketUiState = TiketUiState.Loading
            try {
                val daftarTiket = tiketRepo.getTiket().map { tiket ->
                    val event = eventRepo.getEventById(tiket.idEvent)
                    tiket.copy(
                        tanggalEvent = event?.tanggalEvent ?: "Tidak Diketahui",
                        lokasiEvent = event?.lokasiEvent ?: "Tidak Diketahui"
                    )
                }
                tiketUiState = TiketUiState.Success(daftarTiket)
            } catch (e: IOException) {
                tiketUiState = TiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = TiketUiState.Error
            }
        }
    }

    fun deleteTiket(idTiket: String) {
        viewModelScope.launch {
            try {
                tiketRepo.deleteTiket(idTiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = TiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = TiketUiState.Error
            }
        }
    }
}