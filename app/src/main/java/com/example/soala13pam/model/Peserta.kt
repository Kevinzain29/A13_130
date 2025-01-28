package com.example.soala13pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Peserta(
    @SerialName("id_peserta") val idPeserta: String,
    @SerialName("nama_peserta") val namaPeserta: String,
    val email: String,
    @SerialName("nomor_telepon") val nomorTelepon: String
)