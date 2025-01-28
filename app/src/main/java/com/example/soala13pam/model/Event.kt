package com.example.soala13pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("id_event") val idEvent: String,
    @SerialName("nama_event") val namaEvent: String,
    @SerialName("deskripsi_event") val deskripsiEvent: String,
    @SerialName("tanggal_event") val tanggalEvent: String,
    @SerialName("lokasi_event") val lokasiEvent: String
)