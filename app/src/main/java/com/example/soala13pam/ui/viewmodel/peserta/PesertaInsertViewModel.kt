package com.example.soala13pam.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.repository.PesertaRepository
import kotlinx.coroutines.launch

class PesertaInsertViewModel(private val pesertaRepo: PesertaRepository) : ViewModel() {
    var uiState by mutableStateOf(PesertaInsertUiState())
        private set

    fun updateInsertPesertaState(insertUiEvent: PesertaInsertUiEvent) {
        uiState = PesertaInsertUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPeserta() {
        viewModelScope.launch {
            try {
                pesertaRepo.insertPeserta(uiState.insertUiEvent.toPeserta())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class PesertaInsertUiState(
    val insertUiEvent: PesertaInsertUiEvent = PesertaInsertUiEvent()
)

data class PesertaInsertUiEvent(
    val idPeserta: String = "",
    val namaPeserta: String = "",
    val email: String = "",
    val nomorTelepon: String = ""
)

fun PesertaInsertUiEvent.toPeserta(): Peserta = Peserta(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)

fun Peserta.toUiStatePeserta(): PesertaInsertUiState = PesertaInsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Peserta.toInsertUiEvent(): PesertaInsertUiEvent = PesertaInsertUiEvent(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)
