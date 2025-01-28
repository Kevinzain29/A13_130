package com.example.soala13pam.ui.viewmodel.peserta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.repository.PesertaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class PesertaDetailUiState {
    data class Success(val peserta: Peserta) : PesertaDetailUiState()
    object Error : PesertaDetailUiState()
    object Loading : PesertaDetailUiState()
}

class PesertaDetailViewModel(private val repository: PesertaRepository) : ViewModel() {
    private val _detailUiState = MutableStateFlow<PesertaDetailUiState>(PesertaDetailUiState.Loading)
    val detailUiState: StateFlow<PesertaDetailUiState> = _detailUiState

    fun getPesertaById(idPeserta: String) {
        viewModelScope.launch {
            _detailUiState.value = PesertaDetailUiState.Loading
            try {
                val peserta = repository.getPesertaById(idPeserta)
                _detailUiState.value = PesertaDetailUiState.Success(peserta)
            } catch (e: IOException) {
                e.printStackTrace()
                _detailUiState.value = PesertaDetailUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _detailUiState.value = PesertaDetailUiState.Error
            }
        }
    }
}