package com.example.soala13pam.ui.viewmodel.transaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.repository.EventRepository
import com.example.soala13pam.repository.PesertaRepository
import com.example.soala13pam.repository.TiketRepository
import com.example.soala13pam.repository.TransaksiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class TransaksiDetailUiState {
    data class Success(val transaksi: TransaksiFULL) : TransaksiDetailUiState()
    object Error : TransaksiDetailUiState()
    object Loading : TransaksiDetailUiState()
}

class TransaksiDetailViewModel(
    private val repository: TransaksiRepository,
    private val eventRepo: EventRepository,
    private val pesertaRepo: PesertaRepository,
    private val tiketRepo: TiketRepository
) : ViewModel() {
    private val _detailUiStateT =
        MutableStateFlow<TransaksiDetailUiState>(TransaksiDetailUiState.Loading)
    val detailUiStateT: StateFlow<TransaksiDetailUiState> = _detailUiStateT

    fun getTransaksiById(idTransaksi: String) {
        viewModelScope.launch {
            _detailUiStateT.value = TransaksiDetailUiState.Loading
            try {
                val transaksi = repository.getTransaksiById(idTransaksi)
                val tiket = tiketRepo.getTiketById(transaksi.idTiket)
                val event = eventRepo.getEventById(tiket.idEvent)
                val peserta = pesertaRepo.getPesertaById(tiket.idPeserta)

                val kombinasi = TransaksiFULL(transaksi, tiket, peserta, event)

                _detailUiStateT.value = TransaksiDetailUiState.Success(kombinasi)
            } catch (e: IOException) {
                _detailUiStateT.value = TransaksiDetailUiState.Error
            } catch (e: HttpException) {
                _detailUiStateT.value = TransaksiDetailUiState.Error
            }
        }
    }
}