package com.example.soala13pam.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class PesertaUiState {
    data class Success(val peserta: List<Peserta>) : PesertaUiState()
    object Error : PesertaUiState()
    object Loading : PesertaUiState()
}

class PesertaHomeViewModel(private val pesertaRepo: PesertaRepository) : ViewModel() {
    var pesertaUiState: PesertaUiState by mutableStateOf(PesertaUiState.Loading)
        private set

    init {
        getPeserta()
    }

    fun getPeserta() {
        viewModelScope.launch {
            pesertaUiState = PesertaUiState.Loading
            pesertaUiState = try {
                PesertaUiState.Success(pesertaRepo.getPeserta())
            } catch (e: IOException) {
                PesertaUiState.Error
            } catch (e: HttpException) {
                PesertaUiState.Error
            }
        }
    }

    fun deletePeserta(idPeserta: String) {
        viewModelScope.launch {
            try {
                pesertaRepo.deletePeserta(idPeserta)
                getPeserta()
            } catch (e: IOException) {
                PesertaUiState.Error
            } catch (e: HttpException) {
                PesertaUiState.Error
            }
        }
    }
}
