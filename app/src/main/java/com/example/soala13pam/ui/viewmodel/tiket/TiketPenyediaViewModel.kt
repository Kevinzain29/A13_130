package com.example.soala13pam.ui.viewmodel.tiket

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.soala13pam.EventApplications
import com.example.soala13pam.ui.viewmodel.peserta.aplikasiEVENT

object TiketPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { TiketHomeViewModel(
            aplikasiEVENT().container.tiketRepository,
            aplikasiEVENT().container.eventRepository
        ) }
        initializer { TiketInsertViewModel(
            aplikasiEVENT().container.tiketRepository,
            aplikasiEVENT().container.eventRepository,
            aplikasiEVENT().container.pesertaRepository
        ) }
        initializer { TiketDetailViewModel(
            aplikasiEVENT().container.tiketRepository,
            aplikasiEVENT().container.eventRepository
        ) }
        initializer { TiketUpdateViewModel(aplikasiEVENT().container.tiketRepository) }
    }
}

fun CreationExtras.aplikasiEVENT(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)