package com.example.soala13pam.repository

import com.example.soala13pam.model.Transaksi
import com.example.soala13pam.serviceAPI.TransaksiService
import java.io.IOException

interface TransaksiRepository {
    suspend fun getTransaksi(): List<Transaksi>
    suspend fun insertTransaksi(transaksi: Transaksi)
    suspend fun updateTransaksi(idTransaksi: String, transaksi: Transaksi)
    suspend fun deleteTransaksi(idTransaksi: String)
    suspend fun getTransaksiById(idTransaksi: String): Transaksi
}

class NetworkTransaksiRepository(
    private val transaksiAPIService: TransaksiService
) : TransaksiRepository {
    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiAPIService.insertTransaksi(transaksi)
    }

    override suspend fun updateTransaksi(idTransaksi: String, transaksi: Transaksi) {
        transaksiAPIService.updateTransaksi(idTransaksi, transaksi)
    }

    override suspend fun deleteTransaksi(idTransaksi: String) {
        try {
            val response = transaksiAPIService.deleteTransaksi(idTransaksi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete transaksi. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTransaksi(): List<Transaksi> = transaksiAPIService.getTransaksi()
    override suspend fun getTransaksiById(idTransaksi: String): Transaksi {
        return transaksiAPIService.getTransaksiById(idTransaksi)
    }
}