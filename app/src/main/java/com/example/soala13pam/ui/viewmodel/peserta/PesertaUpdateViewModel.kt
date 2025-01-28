package com.example.soala13pam.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.repository.PesertaRepository
import kotlinx.coroutines.launch

class PesertaUpdateViewModel(private val pesertaRepo: PesertaRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateUiState())
        private set
    fun loadPeserta(idPeserta: String) {
        viewModelScope.launch {
            try {
                val Peserta = pesertaRepo.getPesertaById(idPeserta)
                uiState = UpdateUiState(updateUiEvent = Peserta.toUpdateUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updatePesertaState(updateUiEvent: UpdateUiEvent) {
        uiState = UpdateUiState(updateUiEvent = updateUiEvent)
    }
    suspend fun updatePeserta(idPeserta: String) {
        viewModelScope.launch {
            try {
                pesertaRepo.updatePeserta(idPeserta, uiState.updateUiEvent.toPeserta())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent()
)

data class UpdateUiEvent(
    val idPeserta: String = "",
    val namaPeserta: String = "",
    val email: String = "",
    val nomorTelepon: String = ""
)

fun UpdateUiEvent.toPeserta(): Peserta = Peserta(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)

fun Peserta.toUpdateUiEvent(): UpdateUiEvent = UpdateUiEvent(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)