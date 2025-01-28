package com.example.soala13pam.ui.viewmodel.tiket

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Event
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.repository.EventRepository
import com.example.soala13pam.repository.PesertaRepository
import com.example.soala13pam.repository.TiketRepository
import kotlinx.coroutines.launch

class TiketInsertViewModel(
    private val tiketRepo: TiketRepository,
    private val eventRepo: EventRepository,
    private val pesertaRepo: PesertaRepository
) : ViewModel() {
    var uiState by mutableStateOf(TiketInsertUiState())
        private set

    var listEvent by mutableStateOf(listOf<Event>())
        private set

    var listPeserta by mutableStateOf(listOf<Peserta>())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        EventAndPeserta()
    }
    private fun EventAndPeserta() {
        viewModelScope.launch {
            isLoading = true
            try {
                val events = eventRepo.getEvent()
                val peserta = pesertaRepo.getPeserta()
                listEvent = events
                listPeserta = peserta
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun updateInsertTiketState(insertUiEvent3: TiketInsertUiEvent) {
        uiState = TiketInsertUiState(insertUiEvent3 = insertUiEvent3)
    }

    fun insertTiket() {
        viewModelScope.launch {
            try {
                tiketRepo.insertTiket(uiState.insertUiEvent3.toTiket())
                Log.d("Hasil", "berhasil")
                Log.d("Hasil",uiState.insertUiEvent3.toString())
            } catch (e: Exception) {
                Log.d("Hasil", uiState.insertUiEvent3.toString())
                Log.d("Hasil", "Gagal")
                e.printStackTrace()
            }
        }
    }
}

data class TiketInsertUiState(
    val insertUiEvent3: TiketInsertUiEvent = TiketInsertUiEvent()
)

data class TiketInsertUiEvent(
    val idTiket: String = "",
    val idEvent: String = "",
    val idPeserta: String = "",
    val kapasitasTiket: String = "",
    val hargaTiket: String = ""
)

fun TiketInsertUiEvent.toTiket(): Tiket = Tiket(
    idTiket = idTiket,
    idEvent = idEvent,
    idPeserta = idPeserta,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)

fun Tiket.toUiStateTiket(): TiketInsertUiState = TiketInsertUiState(
    insertUiEvent3 = toInsertUiEvent3()
)

fun Tiket.toInsertUiEvent3(): TiketInsertUiEvent = TiketInsertUiEvent(
    idTiket = idTiket,
    idEvent = idEvent,
    idPeserta = idPeserta,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)