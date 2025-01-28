package com.example.soala13pam.ui.viewmodel.peserta

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.soala13pam.EventApplications

object PesertaPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { PesertaHomeViewModel(aplikasiEVENT().container.pesertaRepository) }
        initializer { PesertaInsertViewModel(aplikasiEVENT().container.pesertaRepository) }
        initializer { PesertaDetailViewModel(aplikasiEVENT().container.pesertaRepository) }
        initializer { PesertaUpdateViewModel(aplikasiEVENT().container.pesertaRepository) }
    }
}

fun CreationExtras.aplikasiEVENT(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)