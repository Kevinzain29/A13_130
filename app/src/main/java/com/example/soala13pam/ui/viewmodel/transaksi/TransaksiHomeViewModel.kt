package com.example.soala13pam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.soala13pam.model.Event
import com.example.soala13pam.model.Peserta
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.model.Transaksi
import com.example.soala13pam.repository.EventRepository
import com.example.soala13pam.repository.PesertaRepository
import com.example.soala13pam.repository.TiketRepository
import com.example.soala13pam.repository.TransaksiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class TransaksiUiState {
    data class Success(val transaksi: List<TransaksiFULL>) : TransaksiUiState()
    object Error : TransaksiUiState()
    object Loading : TransaksiUiState()
}

class TransaksiHomeViewModel(
    private val transaksiRepo: TransaksiRepository,
    private val pesertaRepo: PesertaRepository,
    private val eventRepo: EventRepository,
    private val tiketRepo: TiketRepository

    ) : ViewModel() {
    var transaksiUiState: TransaksiUiState by mutableStateOf(TransaksiUiState.Loading)
        private set

    init {
        getTransaksi()
    }
    fun getTransaksi() {
        viewModelScope.launch {
            transaksiUiState = TransaksiUiState.Loading
            try {
                val transaksiList = transaksiRepo.getTransaksi()
                val tiketList = tiketRepo.getTiket()
                val eventList = eventRepo.getEvent()
                val pesertaList = pesertaRepo.getPeserta()

                val kombinasi = transaksiList.map { id ->
                    val tiket = tiketList.find { it.idTiket == id.idTiket }
                    val event = eventList.find { it.idEvent == tiket?.idEvent }
                    val peserta = pesertaList.find { it.idPeserta == tiket?.idPeserta }
                    TransaksiFULL(id, tiket, peserta, event)
                }
                transaksiUiState = TransaksiUiState.Success(kombinasi)
            } catch (e: IOException) {
                transaksiUiState = TransaksiUiState.Error
            } catch (e: HttpException) {
                transaksiUiState = TransaksiUiState.Error
            }
        }
    }

    fun deleteTransaksi(idTransaksi: String) {
        viewModelScope.launch {
            try {
                transaksiRepo.deleteTransaksi(idTransaksi)
                getTransaksi()
            } catch (e: IOException) {
                TransaksiUiState.Error
            } catch (e: HttpException) {
                TransaksiUiState.Error
            }
        }
    }
}

data class TransaksiFULL (
    val transaksi: Transaksi,
    val tiket: Tiket?,
    val peserta: Peserta?,
    val event: Event?
)