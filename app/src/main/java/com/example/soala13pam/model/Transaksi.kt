package com.example.soala13pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transaksi(
    @SerialName("id_transaksi") val idTransaksi: String,
    @SerialName("id_tiket") val idTiket: String,
    @SerialName("jumlah_tiket") val jumlahTiket: String,
    @SerialName("jumlah_pembayaran") val jumlahPembayaran: String,
    @SerialName("tanggal_transaksi") val tanggalTransaksi: String,
)