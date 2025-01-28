package com.example.soala13pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tiket(
    @SerialName("id_tiket") val idTiket: String,
    @SerialName("id_event") val idEvent: String,
    @SerialName("id_peserta") val idPeserta: String,
    @SerialName("Kapasitas_tiket") val kapasitasTiket: String,
    @SerialName("harga_tiket") val hargaTiket: String,
)