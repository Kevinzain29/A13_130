package com.example.soala13pam.ui.viewmodel.transaksi

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.soala13pam.EventApplications
import com.example.soala13pam.ui.viewmodel.peserta.aplikasiEVENT

object TransaksiPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { TransaksiHomeViewModel(
            aplikasiEVENT().container.transaksiRepository,
            aplikasiEVENT().container.pesertaRepository,
            aplikasiEVENT().container.eventRepository,
            aplikasiEVENT().container.tiketRepository
        ) }
        initializer { TransaksiInsertViewModel(
            aplikasiEVENT().container.transaksiRepository,
            aplikasiEVENT().container.tiketRepository
        ) }
        initializer { TransaksiDetailViewModel(
            aplikasiEVENT().container.transaksiRepository,
            aplikasiEVENT().container.eventRepository,
            aplikasiEVENT().container.pesertaRepository,
            aplikasiEVENT().container.tiketRepository
        ) }
    }
}

fun CreationExtras.aplikasiEVENT(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)