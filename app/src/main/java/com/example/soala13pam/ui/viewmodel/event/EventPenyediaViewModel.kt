package com.example.soala13pam.ui.viewmodel.event

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.soala13pam.EventApplications


object EventPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { EventHomeViewModel(aplikasiEVENT().container.eventRepository) }
        initializer { EventInsertViewModel(aplikasiEVENT().container.eventRepository) }
        initializer { EventDetailViewModel(aplikasiEVENT().container.eventRepository) }
        initializer { EventUpdateViewModel(aplikasiEVENT().container.eventRepository) }
    }
}

fun CreationExtras.aplikasiEVENT(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)