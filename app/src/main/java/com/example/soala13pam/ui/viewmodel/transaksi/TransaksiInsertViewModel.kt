package com.example.soala13pam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soala13pam.model.Tiket
import com.example.soala13pam.model.Transaksi
import com.example.soala13pam.repository.TiketRepository
import com.example.soala13pam.repository.TransaksiRepository
import kotlinx.coroutines.launch

class TransaksiInsertViewModel(
    private val transaksiRepo: TransaksiRepository,
    private val tiketRepo: TiketRepository,
) : ViewModel() {
    var uiState by mutableStateOf(TransaksiInsertUiState())
        private set

    var listTiket by mutableStateOf(listOf<Tiket>())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        AndTiket()
    }
    private fun AndTiket() {
        viewModelScope.launch {
            isLoading = true
            try {
                val tikets = tiketRepo.getTiket()
                listTiket = tikets
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
    // Fungsi untuk menghitung jumlah pembayaran
    private fun calculateJumlahPembayaran(jumlahTiket: String, idTiket: String): String {
        val tiket = listTiket.find { it.idTiket == idTiket }
        val hargaTiket = tiket?.hargaTiket?.toDoubleOrNull() ?: 0.0
        val jumlahTiketInt = jumlahTiket.toIntOrNull() ?: 0
        return (hargaTiket * jumlahTiketInt).toString()
    }
    fun updateInsertTransaksiState(insertUiEvent4: TransaksiInsertUiEvent) {
        val updatedState = insertUiEvent4.copy(
            jumlahPembayaran = calculateJumlahPembayaran(insertUiEvent4.jumlahTiket, insertUiEvent4.idTiket)
        )
        uiState = TransaksiInsertUiState(insertUiEvent4 = updatedState)
    }

    fun insertTransaksi() {
        viewModelScope.launch {
            try {
                transaksiRepo.insertTransaksi(uiState.insertUiEvent4.toEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class TransaksiInsertUiState(
    val insertUiEvent4: TransaksiInsertUiEvent = TransaksiInsertUiEvent()
)

data class TransaksiInsertUiEvent(
    val idTransaksi: String = "",
    val idTiket: String = "",
    val jumlahTiket: String = "",
    val jumlahPembayaran: String = "",
    val tanggalTransaksi: String = ""
)

fun TransaksiInsertUiEvent.toEvent(): Transaksi = Transaksi(
    idTransaksi = idTransaksi,
    idTiket = idTiket,
    jumlahTiket = jumlahTiket,
    jumlahPembayaran = jumlahPembayaran,
    tanggalTransaksi = tanggalTransaksi
)

fun Transaksi.toUiStateTransaksi(): TransaksiInsertUiState = TransaksiInsertUiState(
    insertUiEvent4 = toInsertUiEvent4()
)

fun Transaksi.toInsertUiEvent4(): TransaksiInsertUiEvent = TransaksiInsertUiEvent(
    idTransaksi = idTransaksi,
    idTiket = idTiket,
    jumlahTiket = jumlahTiket,
    jumlahPembayaran = jumlahPembayaran,
    tanggalTransaksi = tanggalTransaksi
)